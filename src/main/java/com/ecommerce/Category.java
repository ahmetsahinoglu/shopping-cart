package com.ecommerce;

import java.util.Objects;

public class Category {
    private String title;
    private Category parentCategory;

    public Category(String title) {
        this(title, null);
    }

    public Category(String title, Category parentCategory) {
        setTitle(title);
        setParentCategory(parentCategory);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return title.equals(category.title) && Objects.equals(parentCategory, category.parentCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, parentCategory);
    }
}
