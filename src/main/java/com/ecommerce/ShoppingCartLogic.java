package com.ecommerce;

import com.ecommerce.discount.DiscountType;
import com.ecommerce.discount.campaign.Campaign;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public interface ShoppingCartLogic {
    static boolean checkCategories(List<Category> list) {
        return list != null && list.size() > 0;
    }

    static double calculateRateDiscount(double totalPrice, double getDiscountAmount) {
        return totalPrice * getDiscountAmount / 100;
    }

    static List<Category> getCampaignEligibleProducts(Campaign campaign, ShoppingCart cart) {
        List<Category> categories = cart.getShoppingCart().keySet()
                .stream()
                .filter(product -> hasEligibleCategory(product.getCategory(), campaign))
                .map(Product::getCategory)
                .collect(Collectors.toList());

        int countOfCategoryProduct = getTotalProductQuantityByGivenCategories(cart, categories);

        if (campaign.isEligible(countOfCategoryProduct)) {
            return categories;
        }
        return null;
    }

    private static boolean hasEligibleCategory(Category category, Campaign campaign) {
        return category.equals(campaign.getCategory()) ||
                (category.getParentCategory() != null && hasEligibleCategory(category.getParentCategory(), campaign));
    }

    static double getCategoryTotalPrice(ShoppingCart cart, List<Category> eligibleCategories) {
        return getTotalPriceGroupedByCategory(cart).entrySet()
                .stream()
                .filter(c -> eligibleCategories.contains(c.getKey()))
                .mapToDouble(Map.Entry::getValue)
                .sum();
    }

    static Map<Category, Double> getTotalPriceGroupedByCategory(ShoppingCart cart) {
        return cart.getShoppingCart().entrySet().stream()
                .collect(groupingBy(x -> x.getKey().getCategory(),
                        Collectors.summingDouble(x -> x.getKey().getPrice() * x.getValue())));
    }

    static int getTotalProductQuantityByGivenCategories(ShoppingCart cart, List<Category> categories) {
        return cart.getShoppingCart().entrySet()
                .stream()
                .filter(e -> categories.contains(e.getKey().getCategory()))
                .mapToInt(Map.Entry::getValue)
                .sum();
    }

    static double calculateDiscount(double categoryTotalPrice, Campaign campaign) {
        return campaign.getDiscountType().equals(DiscountType.RATE) ?
                calculateRateDiscount(categoryTotalPrice, campaign.getDiscountAmount()) : campaign.getDiscountAmount();
    }
}
