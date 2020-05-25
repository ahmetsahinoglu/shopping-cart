package com.ecommerce.discount.coupon;

import com.ecommerce.discount.DiscountType;

public class RateCoupon implements Coupon {
    private final double discountAmount;
    private final double minPrice;

    public RateCoupon(double discountAmount, double minPrice) {
        this.discountAmount = discountAmount;
        this.minPrice = minPrice;
    }

    @Override
    public boolean isEligible(double totalAmount) {
        return totalAmount >= getMinPrice();
    }

    @Override
    public DiscountType getDiscountType() {
        return DiscountType.RATE;
    }

    @Override
    public double getDiscountAmount() {
        return discountAmount;
    }

    @Override
    public double getMinPrice() {
        return minPrice;
    }
}
