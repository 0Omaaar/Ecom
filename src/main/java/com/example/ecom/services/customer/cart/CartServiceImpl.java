package com.example.ecom.services.customer.cart;

import com.example.ecom.dto.AddProductInCartDto;
import com.example.ecom.dto.CartItemsDto;
import com.example.ecom.dto.OrderDto;
import com.example.ecom.entity.CartItems;
import com.example.ecom.entity.Order;
import com.example.ecom.entity.Product;
import com.example.ecom.entity.User;
import com.example.ecom.enums.OrderStatus;
import com.example.ecom.repository.CartItemsRepository;
import com.example.ecom.repository.OrderRepository;
import com.example.ecom.repository.ProductRepository;
import com.example.ecom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
        return orderDto;
    }
}
