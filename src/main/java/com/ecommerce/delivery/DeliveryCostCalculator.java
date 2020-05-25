package com.ecommerce.delivery;

import com.ecommerce.ShoppingCart;

public class DeliveryCostCalculator {
    private DeliveryCostCalculatorStrategy strategy;

    public DeliveryCostCalculator(DeliveryCostCalculatorStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculateFor(ShoppingCart cart) {
        return strategy.calculateFor(cart);
    }
}
