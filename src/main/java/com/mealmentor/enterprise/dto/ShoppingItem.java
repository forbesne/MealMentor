package com.mealmentor.enterprise.dto;

import lombok.Data;

public @Data
class ShoppingItem {
    private Ingredient ingredient;
    private int quantity;
}
