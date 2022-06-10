package com.example.cnpm.Database;

public class Department {
    private String name;
    private int manager_id;
    private String manager_name;

    public Department(String name, int manager_id, String manager_name){
        this.name = name;
        this.manager_id = manager_id;
        this.manager_name = manager_name;
    }

    public Department(String name, Integer o, String manager_name) {
    }

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

    public String getManager_name() {
        return manager_name;
    }

    public void setManager_name(String manager_name) {
        this.manager_name = manager_name;
    }
}
