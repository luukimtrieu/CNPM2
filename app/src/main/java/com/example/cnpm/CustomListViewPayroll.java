package com.example.cnpm;

public class CustomListViewPayroll {
    private String name;
    private String money;

    public CustomListViewPayroll(String name, String money) {
        this.name = name;
        this.money = money;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMoney() {
        return money;
    }
    public void setMoney(String money) {
        this.money = money;
    }
}
