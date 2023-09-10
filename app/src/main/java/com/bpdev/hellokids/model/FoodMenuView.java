package com.bpdev.hellokids.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FoodMenuView {

    private FoodMenu item;

    public FoodMenuView() {
    }

    public FoodMenuView(FoodMenu item) {
        this.item = item;
    }

    public FoodMenu getItem() {
        return item;
    }

    public void setItem(FoodMenu item) {
        this.item = item;
    }
}
