package com.example.cnpm.Database;

public class Department {
    private String name;
    private int manager_id;
    private int manager_name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getManager_id() {
        return manager_id;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }

    public int getManager_name() {
        return manager_name;
    }

    public void setManager_name(int manager_name) {
        this.manager_name = manager_name;
    }
}
