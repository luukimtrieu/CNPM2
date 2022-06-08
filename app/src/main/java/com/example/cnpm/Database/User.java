package com.example.cnpm.Database;

public class User {
    private String company_name;
    private String user_name;
    private String email;
    private String password;
    private int employee_id;

    public User(String company_name, String user_name, String email, String password, int employee_id) {
        this.company_name = company_name;
        this.user_name = user_name;
        this.email = email;
        this.password = password;
        this.employee_id = employee_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
