package com.bpdev.hellokids.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FoodMenuView {

    private FoodMenu items;

    public FoodMenuView() {
    }

    public FoodMenuView(FoodMenu item) {
        this.items = items;
    }

    public FoodMenu getItem() {
        return items;
    }

    public void setItem(FoodMenu item) {
        this.items = items;
    }
}
