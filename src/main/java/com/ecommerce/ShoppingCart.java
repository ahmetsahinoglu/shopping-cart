package com.ecommerce;

import com.ecommerce.delivery.DeliveryCostCalculator;
import com.ecommerce.delivery.StandardDeliveryCostCostCalculator;
import com.ecommerce.discount.DiscountType;
import com.ecommerce.discount.campaign.Campaign;
import com.ecommerce.discount.coupon.Coupon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public class ShoppingCart implements Cart {
    private static final double FIXED_COST = 2.99;
    private static final double COST_PER_DELIVERY = 2;
    private static final double COST_PER_PRODUCT = 1;

    private static final Map<Product, Integer> shoppingCart;

    private double campaignDiscount = 0;
    private double couponDiscount = 0;

    static {
        shoppingCart = new HashMap<>();
    }

    public void addItem(Product product, int amount) {
        int existAmount = getShoppingCart().getOrDefault(product, 0);
        getShoppingCart().put(product, existAmount + amount);
    }

    public Map<Product, Integer> getShoppingCart() {
        return shoppingCart;
    }

    public void abort() {
        getShoppingCart().clear();
    }

    public void applyDiscounts(Campaign... campaigns) {
        for (Campaign campaign : campaigns) {
            applyCategoryDiscount(campaign);
        }
    }

    public void applyCategoryDiscount(Campaign campaign) {

        List<Category> eligibleCategories = ShoppingCartLogic.getCampaignEligibleProducts(campaign, this);

        if (ShoppingCartLogic.checkCategories(eligibleCategories)) {
            double categoryTotalPrice = ShoppingCartLogic.getCategoryTotalPrice(this, eligibleCategories);
            double campaignDiscount = ShoppingCartLogic.calculateDiscount(categoryTotalPrice, campaign);
            setCampaignDiscount(getCampaignDiscount() + campaignDiscount);
        }
    }


    public void applyCoupon(Coupon coupon) {
        if (coupon.isEligible(getTotalAmountAfterDiscounts())) {
            double couponDiscount = coupon.getDiscountType().equals(DiscountType.RATE) ?
                    ShoppingCartLogic.calculateRateDiscount(getTotalAmountAfterDiscounts(), coupon.getDiscountAmount()) : coupon.getDiscountAmount();

            setCouponDiscount(getCouponDiscount() + couponDiscount);
        }
    }

    public double getTotalDiscount() {
        return getCampaignDiscount() + getCouponDiscount();
    }

    public void setCampaignDiscount(double campaignDiscount) {
        this.campaignDiscount = campaignDiscount;
    }

    public void setCouponDiscount(double couponDiscount) {
        this.couponDiscount = couponDiscount;
    }

    public double getTotalAmount() {
        return getShoppingCart().entrySet()
                .stream()
                .map(i -> i.getKey().getPrice() * i.getValue())
                .mapToDouble(x -> x)
                .sum();
    }

    public double getDeliveryCost() {
        DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(
                new StandardDeliveryCostCostCalculator(COST_PER_DELIVERY, COST_PER_PRODUCT, FIXED_COST));
        return deliveryCostCalculator.calculateFor(this);
    }

    public double getCampaignDiscount() {
        return campaignDiscount;
    }

    public double getCouponDiscount() {
        return couponDiscount;
    }

    public double getTotalAmountAfterDiscounts() {
        return getTotalAmount() - getTotalDiscount();
    }

    public void print() {
        Map<Product, Integer> cart = getShoppingCart();
        Map<Category, List<Product>> result = cart.keySet().stream().collect(groupingBy(Product::getCategory));
        System.out.format("%15s%15s%15s%15s%15s%35s%n", "CategoryName", "ProductName", "Quantity", "UnitPrice", "TotalPrice", "TotalDiscount(coupon,campaign)");
        result.forEach((category, products) ->
                products.forEach(product -> {
                    System.out.format("%15s%15s%15d%15.2f%15.2f%35s%n", category.getTitle(), product.getTitle(), getShoppingCart().get(product), product.getPrice(), getShoppingCart().get(product) * product.getPrice(), "");
                }));
        System.out.format("%15s%15s%15s%15s%15s%35.2f%n", "", "", "", "", "", getTotalDiscount());
        System.out.format("%n%n%n%15s%15s%n", "ShoppingCartTotalPrice", "DeliveryCost");
        System.out.format("%20.2f%20.2f%n", getTotalAmountAfterDiscounts(), getDeliveryCost());
    }


}