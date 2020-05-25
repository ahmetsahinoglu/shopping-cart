package com.ecommerce.delivery;

import com.ecommerce.Category;
import com.ecommerce.Product;
import com.ecommerce.ShoppingCart;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Field;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class StandardDeliveryCostCalculatorTest {
    private DeliveryCostCalculator deliveryCostCalculator;
    private StandardDeliveryCostCostCalculator strategy;
    private ShoppingCart cart;
    private Category fruitCategory;
    private Category technologyCategory;
    private Product apple;
    private Product phone;

    private static Field FIXED_COST;
    private static Field COST_PER_DELIVERY;
    private static Field COST_PER_PRODUCT;

    private double fixedCost;
    private double costPerDelivery;
    private double costPerProduct;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    static {
        try {
            FIXED_COST = ShoppingCart.class.getDeclaredField("FIXED_COST");
            COST_PER_DELIVERY = ShoppingCart.class.getDeclaredField("COST_PER_DELIVERY");
            COST_PER_PRODUCT = ShoppingCart.class.getDeclaredField("COST_PER_PRODUCT");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setup() throws IllegalAccessException {
        FIXED_COST.setAccessible(true);
        COST_PER_DELIVERY.setAccessible(true);
        COST_PER_PRODUCT.setAccessible(true);

        fixedCost = (double) FIXED_COST.get(ShoppingCart.class);
        costPerDelivery = (double) COST_PER_DELIVERY.get(ShoppingCart.class);
        costPerProduct = (double) COST_PER_PRODUCT.get(ShoppingCart.class);

        strategy = new StandardDeliveryCostCostCalculator(costPerDelivery, costPerProduct, fixedCost);
        deliveryCostCalculator = new DeliveryCostCalculator(strategy);

        cart = new ShoppingCart();
        cart.abort();

        fruitCategory = new Category("Fruit");
        technologyCategory = new Category("Technology");

        apple = new Product("Apple", 100.0, fruitCategory);
        phone = new Product("Phone", 1500.0, technologyCategory);

    }

    @Test
    public void it_should_throw_exception_when_cart_is_empty() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Cart can not be empty!");
        cart.getDeliveryCost();
    }

    public void it_should_calculate_delivery_cost() {
        cart.addItem(apple, 2);
        cart.addItem(phone, 1);

        double expectedDeliveryCost = (costPerDelivery * strategy.getNumberOfDeliveries(cart)) + (costPerProduct * strategy.getNumberOfProducts(cart)) + fixedCost;

        assertThat(deliveryCostCalculator.calculateFor(cart), is(equalTo(expectedDeliveryCost)));
    }


}
