package com.ecommerce.discount.coupon;

import com.ecommerce.discount.DiscountType;

public interface Coupon {

    boolean isEligible(double totalAmount);

    DiscountType getDiscountType();

    double getDiscountAmount();

    double getMinPrice();
}
