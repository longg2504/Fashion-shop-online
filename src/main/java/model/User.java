package model;

public class User {
    int user_id;
    String first_name;
    String last_name;
    String user_email;
    String user_pass;

    String phone;
    String address;
    String role;

    String image;

    public User() {
    }

    public User(int user_id, String first_name,String last_name, String user_email, String user_pass, String role) {
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.user_email = user_email;
        this.user_pass = user_pass;
        this.role = role;

    }

    public User(int user_id, String first_name, String last_name, String user_email, String user_pass,  String role, String image) {
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.user_email = user_email;
        this.user_pass = user_pass;
        this.role = role;
        this.image = image;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User(String last_name) {
        this.last_name = last_name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
