package com.ecommerce;

import com.ecommerce.discount.CampaignFactory;
import com.ecommerce.discount.CouponFactory;
import com.ecommerce.discount.DiscountType;
import com.ecommerce.discount.campaign.Campaign;
import com.ecommerce.discount.coupon.Coupon;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class ShoppingCartTest {

    private ShoppingCart cart;
    private Category fruitCategory;
    private Category tropicalCategory;
    private Category technologyCategory;

    private Product apple;
    private Product banana;
    private Product phone;

    Campaign fruitCampaign;
    Campaign tropicalCampaign;
    Campaign technologyCampaign;

    Coupon rateCoupon;
    Coupon amountCoupon;

    @Before
    public void setup() {
        cart = new ShoppingCart();
        cart.abort();

        fruitCategory = new Category("Fruit");
        tropicalCategory = new Category("Tropical", fruitCategory);
        technologyCategory = new Category("Technology");

        apple = new Product("Apple", 100.0, fruitCategory);
        banana = new Product("Coconut", 150.0, tropicalCategory);
        phone = new Product("Phone", 1500.0, technologyCategory);

        fruitCampaign = CampaignFactory.getCampaign(DiscountType.RATE, 20.0, fruitCategory, 3);
        tropicalCampaign = CampaignFactory.getCampaign(DiscountType.RATE, 10.0, tropicalCategory, 1);
        technologyCampaign = CampaignFactory.getCampaign(DiscountType.AMOUNT, 10.0, technologyCategory, 1);

        rateCoupon = CouponFactory.getCoupon(DiscountType.RATE, 100, 10);
        amountCoupon = CouponFactory.getCoupon(DiscountType.AMOUNT, 100, 10);
    }

    @Test
    public void it_should_add_product_to_cart() {
        Category stationeryCategory = new Category("Stationery");

        Product paper = new Product("Paper", 5.0, stationeryCategory);
        cart.addItem(paper, 10);

        assertThat(cart.getShoppingCart().keySet().size(), is(equalTo(1)));
        assertThat(cart.getShoppingCart().get(paper), is(equalTo(10)));
    }

    @Test
    public void it_should_delete_all_product_in_cart() {
        Category stationeryCategory = new Category("Stationery");

        Product paper = new Product("Paper", 5.0, stationeryCategory);
        cart.addItem(paper, 10);
        assertThat(cart.getShoppingCart().keySet().size(), is(equalTo(1)));
        assertThat(cart.getShoppingCart().get(paper), is(equalTo(10)));
        cart.abort();
        assertThat(cart.getShoppingCart().keySet().size(), is(equalTo(0)));
    }

    @Test
    public void should_also_apply_parent_category_campaign_to_child_category() {
        cart.addItem(apple, 2);
        cart.addItem(banana, 1);
        cart.addItem(phone, 1);

        assertThat(cart.getShoppingCart().keySet().size(), is(equalTo(3)));

        assertThat(cart.getShoppingCart().get(apple), is(equalTo(2)));
        assertThat(cart.getShoppingCart().get(banana), is(equalTo(1)));
        assertThat(cart.getShoppingCart().get(phone), is(equalTo(1)));

        assertThat(cart.getTotalAmount(), is(equalTo(1850.0)));
        assertThat(ShoppingCartLogic.getTotalPriceGroupedByCategory(cart).get(fruitCategory), is(equalTo(200.0)));
        assertThat(ShoppingCartLogic.getTotalPriceGroupedByCategory(cart).get(tropicalCategory), is(equalTo(150.0)));
        assertThat(ShoppingCartLogic.getTotalPriceGroupedByCategory(cart).get(technologyCategory), is(equalTo(1500.0)));

        assertThat(cart.getDeliveryCost(), is(equalTo(9.99)));

        assertThat(cart.getTotalAmount(), is(equalTo(1850.0)));
        cart.applyDiscounts(fruitCampaign, technologyCampaign);
        assertThat(cart.getCampaignDiscount(), is(equalTo(80.0)));

        assertThat(cart.getTotalAmountAfterDiscounts(), is(equalTo(1770.0)));

        cart.applyCoupon(rateCoupon);
        cart.applyCoupon(amountCoupon);
        assertThat(cart.getCouponDiscount(), is(equalTo(187.0)));
        assertThat(cart.getTotalAmountAfterDiscounts(), is(equalTo(1583.0)));

        cart.print();
    }

    @Test
    public void should_not_apply_child_category_campaign_to_parent_category() {
        cart.addItem(apple, 2);
        cart.addItem(banana, 1);
        cart.addItem(phone, 1);

        assertThat(cart.getShoppingCart().keySet().size(), is(equalTo(3)));


        assertThat(cart.getShoppingCart().get(apple), is(equalTo(2)));
        assertThat(cart.getShoppingCart().get(banana), is(equalTo(1)));
        assertThat(cart.getShoppingCart().get(phone), is(equalTo(1)));

        assertThat(cart.getTotalAmount(), is(equalTo(1850.0)));
        assertThat(ShoppingCartLogic.getTotalPriceGroupedByCategory(cart).get(fruitCategory), is(equalTo(200.0)));
        assertThat(ShoppingCartLogic.getTotalPriceGroupedByCategory(cart).get(tropicalCategory), is(equalTo(150.0)));
        assertThat(ShoppingCartLogic.getTotalPriceGroupedByCategory(cart).get(technologyCategory), is(equalTo(1500.0)));

        assertThat(cart.getDeliveryCost(), is(equalTo(9.99)));

        assertThat(cart.getTotalAmount(), is(equalTo(1850.0)));
        cart.applyDiscounts(tropicalCampaign);
        assertThat(cart.getCampaignDiscount(), is(equalTo(15.0)));

        assertThat(cart.getTotalAmountAfterDiscounts(), is(equalTo(1835.0)));

        assertThat(cart.getCouponDiscount(), is(equalTo(0.0)));
        assertThat(cart.getTotalAmountAfterDiscounts(), is(equalTo(1835.0)));

        cart.print();
    }

    @Test
    public void should_not_apply_not_eligible_campaign() {
        cart.addItem(apple, 2);
        cart.addItem(banana, 1);

        assertThat(cart.getTotalAmount(), is(equalTo(350.0)));
        cart.applyDiscounts(technologyCampaign);
        assertThat(cart.getCampaignDiscount(), is(equalTo(0.0)));

        assertThat(cart.getTotalAmountAfterDiscounts(), is(equalTo(350.0)));

        cart.print();
    }

}
