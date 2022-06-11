package com.example.cnpm.Database;

public class Work_Info {
    int work_info_id;
    private String work_address;
    private int work_hour;

    public Work_Info(int work_info_id,String work_address, int work_hour){
        this.work_info_id = work_info_id;
        this.work_address = work_address;
        this.work_hour = work_hour;
    }

    public Work_Info() {
    }

    public int getWork_info_id() {
        return work_info_id;
    }

    public void setWork_info_id(int work_info_id) {
        this.work_info_id = work_info_id;
    }

    public String getWork_address() {
        return work_address;
    }

    public void setWork_address(String work_address) {
        this.work_address = work_address;
    }

    public int getWork_hour() {
        return work_hour;
    }

    public void setWork_hour(int work_hour) {
        this.work_hour = work_hour;
    }
}
