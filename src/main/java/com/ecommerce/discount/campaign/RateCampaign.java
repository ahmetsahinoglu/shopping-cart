package com.ecommerce.discount.campaign;

import com.ecommerce.Category;
import com.ecommerce.discount.DiscountType;

public class RateCampaign implements Campaign {
    private final double discountAmount;
    private final Category category;
    private final int quantity;

    public RateCampaign(double discountAmount, Category category, int quantity) {
        this.discountAmount = discountAmount;
        this.category = category;
        this.quantity = quantity;
    }

    @Override
    public boolean isEligible(int quantity) {
        return quantity >= getQuantity();
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
    public Category getCategory() {
        return category;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }
}
