package com.example.ecom.services.admin.adminOrder;

import com.example.ecom.dto.AnalyticsResponse;
import com.example.ecom.dto.OrderDto;
import com.example.ecom.entity.Order;
import com.example.ecom.enums.OrderStatus;
import com.example.ecom.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.spi.CalendarDataProvider;
import java.util.stream.Collectors;

@Service
public class AdminOrderServiceImpl implements AdminOrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<OrderDto> getAllPlacedOrders() {
        List<Order> orderList = orderRepository.findAllByOrderStatusIn(List.of(OrderStatus.Placed, OrderStatus.Shipped,
                OrderStatus.Delivered));

        return orderList.stream().map(Order::getOrderDto).collect(Collectors.toList());
    }

    @Override
    public OrderDto changeOrderStatus(Long orderId, String status) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if(optionalOrder.isPresent()){
            Order order = optionalOrder.get();

            if(status.equals("Shipped")){
                order.setOrderStatus(OrderStatus.Shipped);
            }else if (status.equals("Delivered")){
                order.setOrderStatus(OrderStatus.Delivered);
            }
            return orderRepository.save(order).getOrderDto();
        }
        return null;
    }

    @Override
    public AnalyticsResponse calculateAnalytics() {
        LocalDate currentDate = LocalDate.now();
        LocalDate previousMonthDate = currentDate.minusMonths(1);

        Long currentMonthOrders = getTotalOrdersForMonth(currentDate.getMonthValue(), currentDate.getYear());
        Long previousMonthOrders = getTotalOrdersForMonth(previousMonthDate.getMonthValue(), previousMonthDate.getYear());

        Long currentMonthEarnings = getTotalEarningsForMonth(currentDate.getMonthValue(), currentDate.getYear());
        Long previousMonthEarnings = getTotalEarningsForMonth(previousMonthDate.getMonthValue(), previousMonthDate.getYear());

        Long placed = orderRepository.countByOrderStatus(OrderStatus.Placed);
        Long delivered = orderRepository.countByOrderStatus(OrderStatus.Delivered);
        Long shipped = orderRepository.countByOrderStatus(OrderStatus.Shipped);


        AnalyticsResponse analyticsResponse = new AnalyticsResponse();
        analyticsResponse.setCurrentMonthOrders(currentMonthOrders);
        analyticsResponse.setCurrentMonthEarnings(currentMonthEarnings);
        analyticsResponse.setPreviousMonthOrders(previousMonthOrders);
        analyticsResponse.setPreviousMonthEarnings(previousMonthEarnings);
        analyticsResponse.setPlaced(placed);
        analyticsResponse.setDelivered(delivered);
        analyticsResponse.setShipped(shipped);

        return analyticsResponse;
    }

    private Long getTotalEarningsForMonth(int monthValue, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthValue-1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date startOfMonth = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        Date endOfMonth = calendar.getTime();

        List<Order> orders = orderRepository.findByDateBetweenAndOrderStatus(startOfMonth, endOfMonth, OrderStatus.Delivered);

        Long sum = 0L;
        for(Order order: orders){
            sum += order.getAmount();
        }

        return sum;
    }

    private Long getTotalOrdersForMonth(int monthValue, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthValue-1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date startOfMonth = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        Date endOfMonth = calendar.getTime();

        List<Order> orders = orderRepository.findByDateBetweenAndOrderStatus(startOfMonth, endOfMonth, OrderStatus.Delivered);

        return (long)orders.size();
    }
}
