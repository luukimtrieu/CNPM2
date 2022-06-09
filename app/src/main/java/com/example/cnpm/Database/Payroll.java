package com.example.cnpm.Database;

public class Payroll {
    private int employee_id;
    private String month_year;
    private int is_paid;
    private String basic_salary;
    private int OT_hour;
    private String OT_pay;
    private int day_off;
    private String bonus_salary;
    private String total;

    public Payroll(int id, String date, int _is_paid, String _basic_salary,
                   int ot_hour, String ot_pay, int _day_off, String _bonus_salary, String _total) {
        this.employee_id = id;
        this.month_year = date;
        this.is_paid = _is_paid;
        this.basic_salary = _basic_salary;
        this.OT_hour = ot_hour;
        this.OT_pay = ot_pay;
        this.day_off = _day_off;
        this.bonus_salary = _bonus_salary;
        this.total = _total;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getMonth_year() {
        return month_year;
    }

    public void setMonth_year(String month_year) {
        this.month_year = month_year;
    }

    public int getIs_paid() {
        return is_paid;
    }

    public void setIs_paid(int is_paid) {
        this.is_paid = is_paid;
    }

    public String getBasic_salary() {
        return basic_salary;
    }

    public void setBasic_salary(String basic_salary) {
        this.basic_salary = basic_salary;
    }

    public int getOT_hour() {
        return OT_hour;
    }

    public void setOT_hour(int OT_hour) {
        this.OT_hour = OT_hour;
    }

    public String getOT_pay() {
        return OT_pay;
    }

    public void setOT_pay(String OT_pay) {
        this.OT_pay = OT_pay;
    }

    public int getDay_off() {
        return day_off;
    }

    public void setDay_off(int day_off) {
        this.day_off = day_off;
    }

    public String getBonus_salary() {
        return bonus_salary;
    }

    public void setBonus_salary(String bonus_salary) {
        this.bonus_salary = bonus_salary;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
