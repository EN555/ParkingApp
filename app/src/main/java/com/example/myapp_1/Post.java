package com.example.myapp_1;


import android.graphics.Matrix;

import java.util.Objects;

public class Post{

    private String city, street, houseNum, dataFrom, timeFrom, dateTo, timeTo;
    private Matrix photo;
    private boolean weakly;
    private  User user;
    private double price;
    public Post(){ }

    public Post(String city, String street, String houseNum, double price,
                String dataFrom, String timeFrom, String dateTo, String timeTo,
                Matrix photo, boolean weakly, User user) {
        this.city = city;
        this.street = street;
        this.houseNum = houseNum;
        this.price = price;
        this.dataFrom = dataFrom;
        this.timeFrom = timeFrom;
        this.dateTo = dateTo;
        this.timeTo = timeTo;
        this.photo = photo;
        this.weakly = weakly;
        this.user = user;
    }

    // getters
    public String getCity() { return city; }
    public String getStreet() { return street; }
    public String getHouseNum() { return houseNum; }
    public double getPrice() { return price; }
    public String getDataFrom() { return dataFrom; }
    public String getTimeFrom() { return timeFrom; }
    public String getDateTo() { return dateTo; }
    public String getTimeTo() { return timeTo; }
    public Matrix getPhoto() { return photo; }
    public boolean isWeakly() { return weakly; }
    public User getUser() { return user; }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return weakly == post.weakly && Objects.equals(city, post.city) &&
                Objects.equals(street, post.street) && Objects.equals(houseNum, post.houseNum) &&
                Objects.equals(price, post.price) && Objects.equals(dataFrom, post.dataFrom) &&
                Objects.equals(timeFrom, post.timeFrom) && Objects.equals(dateTo, post.dateTo) &&
                Objects.equals(timeTo, post.timeTo) && Objects.equals(photo, post.photo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, street, houseNum, dataFrom, timeFrom, dateTo, timeTo, photo, weakly, user, price);
    }

    @Override
    public String toString() {
        return "Post{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", houseNum='" + houseNum + '\'' +
                ", price='" + price + '\'' +
                ", dataFrom='" + dataFrom + '\'' +
                ", timeFrom='" + timeFrom + '\'' +
                ", dateTo='" + dateTo + '\'' +
                ", timeTo='" + timeTo + '\'' +
                ", photo=" + photo +
                ", weakly=" + weakly +
                ", user=" + user +
                '}';
    }
}
