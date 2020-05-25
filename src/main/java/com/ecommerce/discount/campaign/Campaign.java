package com.ecommerce.discount.campaign;

import com.ecommerce.Category;
import com.ecommerce.discount.DiscountType;

public interface Campaign {

    boolean isEligible(int quantity);

    DiscountType getDiscountType();

    Category getCategory();

    double getDiscountAmount();

    int getQuantity();
}
