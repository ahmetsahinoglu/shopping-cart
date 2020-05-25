package com.ecommerce.delivery;

import com.ecommerce.ShoppingCart;

public interface DeliveryCostCalculatorStrategy {
    double calculateFor(ShoppingCart cart);
}
