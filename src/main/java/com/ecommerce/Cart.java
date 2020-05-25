package com.ecommerce;

public interface Cart {

    double getTotalAmountAfterDiscounts();

    double getDeliveryCost();

    double getCouponDiscount();

    double getCampaignDiscount();
}
