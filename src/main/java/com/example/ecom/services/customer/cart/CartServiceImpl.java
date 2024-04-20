package com.example.ecom.services.customer.cart;

import com.example.ecom.dto.AddProductInCartDto;
import com.example.ecom.dto.CartItemsDto;
import com.example.ecom.dto.OrderDto;
import com.example.ecom.entity.*;
import com.example.ecom.enums.OrderStatus;
import com.example.ecom.exceptions.ValidationException;
import com.example.ecom.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Override
    public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
        Optional<CartItems> optionalCartItems = cartItemsRepository.findByProductIdAndOrderIdAndUserId(addProductInCartDto.getProductId(),
                activeOrder.getId(), addProductInCartDto.getUserId());
        if(optionalCartItems.isPresent()){
            System.out.println("conflict");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }else{
            Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
            Optional<User> optionalUser = userRepository.findById(addProductInCartDto.getUserId());

            if(optionalProduct.isPresent() && optionalUser.isPresent()){
                CartItems cart = new CartItems();
                cart.setProduct(optionalProduct.get());
                cart.setOrder(activeOrder);
                cart.setPrice(optionalProduct.get().getPrice());
                cart.setQuantity(1L);
                cart.setUser(optionalUser.get());
                CartItems updatedCart = cartItemsRepository.save(cart);

                activeOrder.setTotalAmount(activeOrder.getTotalAmount() + cart.getPrice());
                activeOrder.setAmount(activeOrder.getAmount() + cart.getPrice());
                activeOrder.getCartItems().add(cart);

                orderRepository.save(activeOrder);
                System.out.println("fine");
                return ResponseEntity.status(HttpStatus.CREATED).body(cart);
            }else{
                System.out.println("not fine");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Product Not Found !");
            }
        }
    }

    @Override
    public OrderDto getCartByUserId(Long userId) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        List<CartItemsDto> cartItemsDtoList = activeOrder.getCartItems().stream().map(CartItems::getCartDto).collect(Collectors.toList());
        OrderDto orderDto = new OrderDto();
        orderDto.setId(activeOrder.getId());
        orderDto.setAmount(activeOrder.getAmount());
        orderDto.setOrderStatus(activeOrder.getOrderStatus());
        orderDto.setDiscount(activeOrder.getDiscount());
        orderDto.setTotalAmount(activeOrder.getTotalAmount());
        orderDto.setCartItems(cartItemsDtoList);
        if(activeOrder.getCoupon() != null){
            orderDto.setCouponName(activeOrder.getCoupon().getName());
        }


        return orderDto;
    }

    @Override
    public OrderDto applyCoupon(Long userId, String code) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        Coupon coupon = couponRepository.findByCode(code);
        if(coupon == null){
            throw new ValidationException("Coupon Not Found !");
        }
        if(couponIsExpired(coupon)){
            throw new ValidationException("Coupon Expired !");
        }

        double discountAmount = (activeOrder.getTotalAmount() * (coupon.getDiscount() / 100.0));
        double netAmount = activeOrder.getTotalAmount() - discountAmount;

        activeOrder.setAmount((long)netAmount);
        activeOrder.setDiscount((long)discountAmount);
        activeOrder.setCoupon(coupon);

        orderRepository.save(activeOrder);

        return activeOrder.getOrderDto();
    }


    boolean couponIsExpired(Coupon coupon){
        Date dateExp = coupon.getExpirationDate();
        Date currentDate = new Date();

        return dateExp != null && currentDate.after(dateExp);
    }
}
