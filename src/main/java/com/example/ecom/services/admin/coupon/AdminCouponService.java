package com.example.ecom.services.admin.coupon;

import com.example.ecom.dto.OrderDto;
import com.example.ecom.entity.Coupon;
import com.example.ecom.entity.Order;

import java.util.List;

public interface AdminCouponService {

    Coupon createCoupon(Coupon coupon);

    List<Coupon> getAllCoupons();
}
