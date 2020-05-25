package com.ecommerce;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class ProductTest {

    @Test
    public void it_should_create_product_successfully_with_given_parameters() {
        String categoryTitle = "Technology";
        String productTitle = "Phone";
        double productPrice = 450.0;

        Category technologyCategory = new Category(categoryTitle);

        Product phone = new Product(productTitle, productPrice, technologyCategory);

        assertThat(technologyCategory, is(equalTo(phone.getCategory())));
        assertThat(technologyCategory.getTitle(), is(equalTo(phone.getCategory().getTitle())));
        assertThat(productPrice, is(equalTo(phone.getPrice())));
        assertThat(productTitle, is(equalTo(phone.getTitle())));
    }
}
