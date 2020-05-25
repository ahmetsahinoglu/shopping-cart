package com.ecommerce;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class CategoryTest {

    @Test
    public void it_should_create_category_successfully_with_given_parameters() {
        String fruitCategoryTitle = "Fruit";
        String tropicalCategoryTitle = "Tropical";

        Category fruitCategory = new Category(fruitCategoryTitle);
        assertThat(fruitCategoryTitle, is(equalTo(fruitCategory.getTitle())));

        Category tropicalCategory = new Category(tropicalCategoryTitle);
        tropicalCategory.setParentCategory(fruitCategory);
        assertThat(fruitCategory, is(equalTo(tropicalCategory.getParentCategory())));
    }
}
