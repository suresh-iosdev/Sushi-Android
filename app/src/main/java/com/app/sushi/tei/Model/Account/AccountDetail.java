package com.app.sushi.tei.Model.Account;

/**
 * Created by root on 13/4/16.
 */
public class AccountDetail {

    private String customer_id;
    private String customer_company_id;
    private String customer_first_name;
    private String customer_last_name;
    private String customer_email;
    private String customer_phone;


    private String customer_birthdate;
    private String customer_company_name;
    private String customer_address_name;
    private String customer_address_name2;
    private String customer_address_line1;
    private String customer_address_line2;
    private String customer_city;
    private String customer_state;
    private String customer_country;
    private String customer_postal_code;
    private String customer_photo;
    private String customer_notes;
    private String customer_status;
    private String customer_company_address;
    private String customer_company_phone;

    private String customer_reward_point;
    private String client_reward_point;

    private boolean isFbLogin;

    private String customer_pasta_stripe_id_dev ="";
    private String customer_porto_stripe_id_dev ="";
    private String customer_kraft_stripe_id_dev ="";

    private String customer_pasta_stripe_id_live ="";
    private String customer_porto_stripe_id_live ="";
    private String customer_kraft_stripe_id_live ="";


    private String customer_nick_name;
    private String customer_gender;
    private String customer_hobby;
    private String customer_created_on;
    private String customer_membership_type;
    private String customer_type;
    private String customer_unique_id;



    //promotion


    public String getCustomer_unique_id() {
        return customer_unique_id;
    }

    public void setCustomer_unique_id(String customer_unique_id) {
        this.customer_unique_id = customer_unique_id;
    }

    public String getcustomer_porto_stripe_id_dev() {
        return customer_porto_stripe_id_dev;
    }

    public void setcustomer_porto_stripe_id_dev(String customer_porto_stripe_id_dev) {
        this.customer_porto_stripe_id_dev = customer_porto_stripe_id_dev;
    }

    public String getcustomer_kraft_stripe_id_dev() {
        return customer_kraft_stripe_id_dev;
    }

    public void setcustomer_kraft_stripe_id_dev(String customer_kraft_stripe_id_dev) {
        this.customer_kraft_stripe_id_dev = customer_kraft_stripe_id_dev;
    }

    public String getcustomer_pasta_stripe_id_dev() {
        return customer_pasta_stripe_id_dev;
    }

    public void setcustomer_pasta_stripe_id_dev(String customer_pasta_stripe_id_dev) {
        this.customer_pasta_stripe_id_dev = customer_pasta_stripe_id_dev;
    }



    public String getCustomer_pasta_stripe_id_live() {
        return customer_pasta_stripe_id_live;
    }

    public void setCustomer_pasta_stripe_id_live(String customer_pasta_stripe_id_live) {
        this.customer_pasta_stripe_id_live = customer_pasta_stripe_id_live;
    }

    public String getCustomer_porto_stripe_id_live() {
        return customer_porto_stripe_id_live;
    }

    public void setCustomer_porto_stripe_id_live(String customer_porto_stripe_id_live) {
        this.customer_porto_stripe_id_live = customer_porto_stripe_id_live;
    }

    public String getCustomer_kraft_stripe_id_live() {
        return customer_kraft_stripe_id_live;
    }

    public void setCustomer_kraft_stripe_id_live(String customer_kraft_stripe_id_live) {
        this.customer_kraft_stripe_id_live = customer_kraft_stripe_id_live;
    }





    public boolean isFbLogin() {
        return isFbLogin;
    }

    public void setIsFbLogin(boolean isFbLogin) {
        this.isFbLogin = isFbLogin;
    }


    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }

    public String getCustomer_reward_point() {
        return customer_reward_point;
    }

    public void setCustomer_reward_point(String customer_reward_point) {
        this.customer_reward_point = customer_reward_point;
    }

    public String getClient_reward_point() {
        return client_reward_point;
    }

    public void setClient_reward_point(String client_reward_point) {
        this.client_reward_point = client_reward_point;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_company_id() {
        return customer_company_id;
    }

    public void setCustomer_company_id(String customer_company_id) {
        this.customer_company_id = customer_company_id;
    }

    public String getCustomer_first_name() {
        return customer_first_name;
    }

    public void setCustomer_first_name(String customer_first_name) {
        this.customer_first_name = customer_first_name;
    }

    public String getCustomer_last_name() {
        return customer_last_name;
    }

    public void setCustomer_last_name(String customer_last_name) {
        this.customer_last_name = customer_last_name;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getCustomer_birthdate() {
        return customer_birthdate;
    }

    public void setCustomer_birthdate(String customer_birthdate) {
        this.customer_birthdate = customer_birthdate;
    }

    public String getCustomer_company_name() {
        return customer_company_name;
    }

    public void setCustomer_company_name(String customer_company_name) {
        this.customer_company_name = customer_company_name;
    }

    public String getCustomer_address_name() {
        return customer_address_name;
    }

    public void setCustomer_address_name(String customer_address_name) {
        this.customer_address_name = customer_address_name;
    }

    public String getCustomer_address_name2() {
        return customer_address_name2;
    }

    public void setCustomer_address_name2(String customer_address_name2) {
        this.customer_address_name2 = customer_address_name2;
    }

    public String getCustomer_address_line1() {
        return customer_address_line1;
    }

    public void setCustomer_address_line1(String customer_address_line1) {
        this.customer_address_line1 = customer_address_line1;
    }

    public String getCustomer_address_line2() {
        return customer_address_line2;
    }

    public void setCustomer_address_line2(String customer_address_line2) {
        this.customer_address_line2 = customer_address_line2;
    }

    public String getCustomer_city() {
        return customer_city;
    }

    public void setCustomer_city(String customer_city) {
        this.customer_city = customer_city;
    }

    public String getCustomer_state() {
        return customer_state;
    }

    public void setCustomer_state(String customer_state) {
        this.customer_state = customer_state;
    }

    public String getCustomer_country() {
        return customer_country;
    }

    public void setCustomer_country(String customer_country) {
        this.customer_country = customer_country;
    }

    public String getCustomer_postal_code() {
        return customer_postal_code;
    }

    public void setCustomer_postal_code(String customer_postal_code) {
        this.customer_postal_code = customer_postal_code;
    }

    public String getCustomer_photo() {
        return customer_photo;
    }

    public void setCustomer_photo(String customer_photo) {
        this.customer_photo = customer_photo;
    }

    public String getCustomer_notes() {
        return customer_notes;
    }

    public void setCustomer_notes(String customer_notes) {
        this.customer_notes = customer_notes;
    }

    public String getCustomer_status() {
        return customer_status;
    }

    public void setCustomer_status(String customer_status) {
        this.customer_status = customer_status;
    }

    public String getCustomer_company_address() {
        return customer_company_address;
    }

    public void setCustomer_company_address(String customer_company_address) {
        this.customer_company_address = customer_company_address;
    }

    public String getCustomer_company_phone() {
        return customer_company_phone;
    }

    public void setCustomer_company_phone(String customer_company_phone) {
        this.customer_company_phone = customer_company_phone;
    }


    //new

    public String getCustomer_nick_name() {
        return customer_nick_name;
    }

    public void setCustomer_nick_name(String customer_nick_name) {
        this.customer_nick_name = customer_nick_name;
    }

    public String getCustomer_gender() {
        return customer_gender;
    }

    public void setCustomer_gender(String customer_gender) {
        this.customer_gender = customer_gender;
    }

    public String getCustomer_hobby() {
        return customer_hobby;
    }

    public void setCustomer_hobby(String customer_hobby) {
        this.customer_hobby = customer_hobby;
    }

    public String getCustomer_created_on() {
        return customer_created_on;
    }

    public void setCustomer_created_on(String customer_created_on) {
        this.customer_created_on = customer_created_on;
    }

    public String getCustomer_membership_type() {
        return customer_membership_type;
    }

    public void setCustomer_membership_type(String customer_membership_type) {
        this.customer_membership_type = customer_membership_type;
    }

    public String getCustomer_type() {
        return customer_type;
    }

    public void setCustomer_type(String customer_type) {
        this.customer_type = customer_type;
    }
}
