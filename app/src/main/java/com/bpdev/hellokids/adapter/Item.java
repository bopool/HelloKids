package com.bpdev.hellokids.adapter;

import com.bpdev.hellokids.model.FoodMenu;

import java.util.List;


// 상위 리사이클러뷰 아이템클래스를 정의
public class Item {
    private String itemTitle;
    // 하위 리사이클러뷰 아이템으로 정의한 subItemList를 전역변수로 선언한다.
    private List<FoodMenu> subItemList;

    public Item(String s, List<FoodMenu> foodMenus) {
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public List<FoodMenu> getSubItemList() {
        return subItemList;
    }

    public void setSubItemList(List<FoodMenu> subItemList) {
        this.subItemList = subItemList;
    }
}