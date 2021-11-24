package utils;


import android.graphics.Bitmap;
import android.net.Uri;

import java.util.Arrays;
import java.util.Objects;

public class Post{

    private String city, street, houseNum, dataFrom, timeFrom, dateTo, timeTo;
    private int[] photo;
    private int photoW, photoH;
    private boolean weakly;
    private User user;
    private double price;
    public Post(){ }

    public Post(String city, String street, String houseNum, double price,
                String dataFrom, String timeFrom, String dateTo, String timeTo,
                int[] photo, int photoW, int photoH, boolean weakly, User user) {
        this.city = city;
        this.street = street;
        this.houseNum = houseNum;
        this.price = price;
        this.dataFrom = dataFrom;
        this.timeFrom = timeFrom;
        this.dateTo = dateTo;
        this.timeTo = timeTo;
        this.photo = photo.clone();
        this.photoW = photoW;
        this.photoH = photoH;
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
    public int[] getPhoto() { return photo; }
    public int getPhotoW() { return photoW; }
    public int getPhotoH() { return photoH; }
    public boolean isWeakly() { return weakly; }
    public User getUser() { return user; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        Post post = (Post) o;
        return photoW == post.photoW && photoH == post.photoH && weakly == post.weakly &&
                Double.compare(post.price, price) == 0 && Objects.equals(city, post.city) &&
                Objects.equals(street, post.street) && Objects.equals(houseNum, post.houseNum) &&
                Objects.equals(dataFrom, post.dataFrom) && Objects.equals(timeFrom, post.timeFrom) &&
                Objects.equals(dateTo, post.dateTo) && Objects.equals(timeTo, post.timeTo) &&
                Arrays.equals(photo, post.photo) && Objects.equals(user, post.user);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(city, street, houseNum, dataFrom, timeFrom, dateTo, timeTo,
                photoW, photoH, weakly, user, price);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }

    @Override
    public String toString() {
        return "Post{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", houseNum='" + houseNum + '\'' +
                ", dataFrom='" + dataFrom + '\'' +
                ", timeFrom='" + timeFrom + '\'' +
                ", dateTo='" + dateTo + '\'' +
                ", timeTo='" + timeTo + '\'' +
                ", photo=" + Arrays.toString(photo) +
                ", photoW=" + photoW +
                ", photoH=" + photoH +
                ", weakly=" + weakly +
                ", user=" + user +
                ", price=" + price +
                '}';
    }
}
