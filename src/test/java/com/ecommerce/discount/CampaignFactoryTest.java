package com.ecommerce.discount;

import com.ecommerce.Category;
import com.ecommerce.discount.campaign.Campaign;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class CampaignFactoryTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void it_should_create_campaign_successfully_with_given_parameters() {
        double discountAmount = 10.0;
        DiscountType discountType = DiscountType.RATE;
        int quantity = 2;
        String categoryTitle = "Technology";

        Category technology = new Category(categoryTitle);
        Campaign campaign = CampaignFactory.getCampaign(discountType, discountAmount, technology, quantity);

        assertThat(technology, is(equalTo(campaign.getCategory())));
        assertThat(technology.getTitle(), is(equalTo(campaign.getCategory().getTitle())));
        assertThat(discountAmount, is(equalTo(campaign.getDiscountAmount())));
        assertThat(quantity, is(equalTo(campaign.getQuantity())));
        assertThat(discountType, is(equalTo(campaign.getDiscountType())));
    }

    @Test
    public void it_should_throw_exception_with_undefined_discount_type() {
        double discountAmount = 10.0;
        DiscountType discountType = DiscountType.NONE;
        int quantity = 2;
        String categoryTitle = "Technology";

        Category technology = new Category(categoryTitle);

        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Campaign type not supported!");

        CampaignFactory.getCampaign(discountType, discountAmount, technology, quantity);
    }
}
