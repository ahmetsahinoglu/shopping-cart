package com.ecommerce.discount;

import com.ecommerce.Category;
import com.ecommerce.discount.DiscountType;
import com.ecommerce.discount.campaign.AmountCampaign;
import com.ecommerce.discount.campaign.Campaign;
import com.ecommerce.discount.campaign.RateCampaign;

public class CampaignFactory {

    public static Campaign getCampaign(final DiscountType discountType, double discountAmount, Category category, int quantity) {
        switch (discountType) {
            case RATE:
                return new RateCampaign(discountAmount, category, quantity);
            case AMOUNT:
                return new AmountCampaign(discountAmount, category, quantity);
            default:
                throw new IllegalArgumentException("Campaign type not supported!");
        }
    }
}
