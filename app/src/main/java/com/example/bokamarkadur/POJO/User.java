package com.example.bokamarkadur.POJO;

import androidx.annotation.Size;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    // Vantar messages

    @SerializedName("id")
    private long id;
    @SerializedName("info")
    private String info;
    @SerializedName("name")
    private String name;
    @SerializedName("username")
    private String username;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("retypePassword")
    private String retypePassword;
    @SerializedName("books")
    private List<Book> books;
    @SerializedName("token")
    private String token;
    @SerializedName("phonenumber")
    private String phonenumber;
    @SerializedName("recievedReviews")
    private List<Review> receivedReviews;
    @SerializedName("writtenReviews")
    private List<Review> writtenReviews;


    // Fjarlægt héðan í bili: List<Message> messages
    public User(long id, String info, String name, String username, String email, String password,
                String retypePassword, List<Book> books, String phonenumber,
                List<Review> receivedReviews, List<Review> writtenReviews) {
        this.id = id;
        this.info = info;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.retypePassword = retypePassword;
        this.books = books;
        this.phonenumber = phonenumber;
        this.receivedReviews = receivedReviews;
        this.writtenReviews = writtenReviews;
    }

    public User(String token) {
        this.token = token;
    }

    public User user;

    public User getUser() {
        return user;
    }

    public long getId() {
        return id;
    }

    public String getInfo() {
        return info;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRetypePassword() {
        return retypePassword;
    }

    public List<Book> getBooks() {
        return books;
    }

    public String getToken() {
        return token;
    }

    public String getPhonenumber() { return phonenumber; }

    public List<Review> getReceivedReviews() { return receivedReviews; }

    public List<Review> getWrittenReviews() { return writtenReviews; }

    public void setId(long id) {
        this.id = id;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRetypePassword(String retypePassword) {
        this.retypePassword = retypePassword;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setPhonenumber(String phonenumber) { this.phonenumber = phonenumber; }

    public void setReceivedReviews(List<Review> receivedReviews) {
        this.receivedReviews = receivedReviews;
    }

    public void setWrittenReviews(List<Review> writtenReviews) {
        this.writtenReviews = writtenReviews;
    }

    @Override
    public String toString() {
        return name;
    }
}
