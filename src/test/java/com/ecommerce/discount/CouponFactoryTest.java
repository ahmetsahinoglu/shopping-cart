package com.ecommerce.discount;


import com.ecommerce.discount.coupon.Coupon;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CouponFactoryTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void it_should_create_coupon_successfully_with_given_parameters() {
        double minPrice = 100.0;
        double discountAmount = 10.0;
        DiscountType discountType = DiscountType.AMOUNT;

        Coupon discountCoupon = CouponFactory.getCoupon(discountType, minPrice, discountAmount);

        assertThat(discountAmount, is(equalTo(discountCoupon.getDiscountAmount())));
        assertThat(discountType, is(equalTo(discountCoupon.getDiscountType())));
        assertThat(discountCoupon.getMinPrice(), is(equalTo(discountCoupon.getMinPrice())));
    }

    @Test
    public void it_should_throw_exception_with_undefined_discount_type() {
        double minPrice = 100.0;
        double discountAmount = 10.0;
        DiscountType discountType = DiscountType.NONE;

        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Coupon type not supported!");

        CouponFactory.getCoupon(discountType, minPrice, discountAmount);
    }
}
