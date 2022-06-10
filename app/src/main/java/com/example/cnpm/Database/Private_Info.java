package com.example.cnpm.Database;

public class Private_Info {
    private String address;
    private String email;
    private String language;
    private String bank_account;
    private String study_field;
    private String gender;
    private String birth;
    private String certificate_level;

    public  Private_Info(String address, String email, String language, String bank_account, String study_field, String gender, String birth, String certificate_level){
        this.address = address;
        this.email = email;
        this.language = language;
        this.bank_account = bank_account;
        this.study_field = study_field;
        this.gender = gender;
        this.birth = birth;
        this.certificate_level = certificate_level;
    }

    public Private_Info() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getBank_account() {
        return bank_account;
    }

    public void setBank_account(String bank_account) {
        this.bank_account = bank_account;
    }

    public String getStudy_field() {
        return study_field;
    }

    public void setStudy_field(String study_field) {
        this.study_field = study_field;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getCertificate_level() {
        return certificate_level;
    }

    public void setCertificate_level(String certificate_level) {
        this.certificate_level = certificate_level;
    }
}
