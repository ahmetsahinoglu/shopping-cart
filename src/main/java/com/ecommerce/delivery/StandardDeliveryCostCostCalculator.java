package com.ecommerce.delivery;


import com.ecommerce.ShoppingCart;

public class StandardDeliveryCostCostCalculator implements DeliveryCostCalculatorStrategy {
    private final double costPerDelivery;
    private final double costPerProduct;
    private final double fixedCost;

    public StandardDeliveryCostCostCalculator(double costPerDelivery, double costPerProduct, double fixedCost) {
        this.costPerDelivery = costPerDelivery;
        this.costPerProduct = costPerProduct;
        this.fixedCost = fixedCost;
    }

    @Override
    public double calculateFor(ShoppingCart cart) {
        if (getNumberOfProducts(cart) == 0) {
            throw new IllegalArgumentException("Cart can not be empty!");
        }
        return (getCostPerDelivery() * getNumberOfDeliveries(cart)) + (getCostPerProduct() * getNumberOfProducts(cart)) + getFixedCost();

    }

    public long getNumberOfDeliveries(ShoppingCart cart) {
        return cart.getShoppingCart().keySet()
                .stream()
                .map(x -> {
                    if (x.getCategory().getParentCategory() != null) {
                        return x.getCategory().getParentCategory();
                    } else {
                        return x.getCategory();
                    }
                })
                .distinct()
                .count();
    }

    public long getNumberOfProducts(ShoppingCart cart) {
        return cart.getShoppingCart().keySet()
                .stream()
                .distinct()
                .count();
    }

    public double getCostPerDelivery() {
        return costPerDelivery;
    }

    public double getCostPerProduct() {
        return costPerProduct;
    }

    public double getFixedCost() {
        return fixedCost;
    }
}
