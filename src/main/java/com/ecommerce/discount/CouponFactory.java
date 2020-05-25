package com.ecommerce.discount;

import com.ecommerce.discount.coupon.AmountCoupon;
import com.ecommerce.discount.coupon.Coupon;
import com.ecommerce.discount.coupon.RateCoupon;

public class CouponFactory {

    public static Coupon getCoupon(final DiscountType discountType, double minPrice, double discountAmount) {
        switch (discountType) {
            case RATE:
                return new RateCoupon(discountAmount, minPrice);
            case AMOUNT:
                return new AmountCoupon(discountAmount, minPrice);
            default:
                throw new IllegalArgumentException("Coupon type not supported!");
        }
    }
}
