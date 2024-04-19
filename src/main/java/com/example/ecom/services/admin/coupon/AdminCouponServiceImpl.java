package com.example.ecom.services.admin.coupon;

import com.example.ecom.entity.Coupon;
import com.example.ecom.exceptions.ValidationException;
import com.example.ecom.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor
public class AdminCouponServiceImpl implements AdminCouponService {

    private final CouponRepository couponRepository;

    @Override
    public Coupon createCoupon(Coupon coupon) {
        if(couponRepository.existsByCode(coupon.getCode())){
            throw new ValidationException("Coupon Code Already Exists !");
        }else{
            return couponRepository.save(coupon);
        }
    }

    @Override
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }
}
