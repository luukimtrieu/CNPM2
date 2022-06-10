package com.example.cnpm.Database;

public class Employee {
    private String name;
    private String department;
    private String work_mobile;
    private String work_email;
    private String manager;
    private String coach;
    private String photo_url;
    private int payroll_id;
    private int work_info_id;
    private int private_info_id;
    private int hr_id;

    public Employee(String _name, String _department, int _payroll, int id) {
        this.name = _name;
        this.department = _department;
        this.work_mobile = "1900 1008";
        this.work_email = "vubaochau@gmail.com";
        this.manager = "Vu Bao Chau";
        this.coach = "Hello";
        this.photo_url = "";
        this.payroll_id = _payroll;
        this.work_info_id = id;
        this.private_info_id = id;
        this.hr_id = id;
    }

    public Employee(String name,String department, String work_mobile, String work_email, String manager, String coach, String photo_url, int payroll_id, int work_info_id, int private_info_id, int hr_id){
        this.name = name;
        this.department = department;
        this.work_mobile = work_mobile;
        this.work_email = work_email;
        this.manager = manager;
        this.coach = coach;
        this.photo_url = photo_url;
        this.payroll_id = payroll_id;
        this.work_info_id = work_info_id;
        this.private_info_id = private_info_id;
        this.hr_id = hr_id;
    }

    public Employee(String name, String department, String work_mobile, String work_email, String manager, String coach, String photo_url, Integer o, int work_info_id, int private_info_id, int hr_id) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getWork_mobile() {
        return work_mobile;
    }

    public void setWork_mobile(String work_mobile) {
        this.work_mobile = work_mobile;
    }

    public String getWork_email() {
        return work_email;
    }

    public void setWork_email(String work_email) {
        this.work_email = work_email;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public int getPayroll_id() {
        return payroll_id;
    }

    public void setPayroll_id(int payroll_id) {
        this.payroll_id = payroll_id;
    }

    public int getWork_info_id() {
        return work_info_id;
    }

    public void setWork_info_id(int work_info_id) {
        this.work_info_id = work_info_id;
    }

    public int getPrivate_info_id() {
        return private_info_id;
    }

    public void setPrivate_info_id(int private_info_id) {
        this.private_info_id = private_info_id;
    }

    public int getHr_id() {
        return hr_id;
    }

    public void setHr_id(int hr_id) {
        this.hr_id = hr_id;
    }
}
