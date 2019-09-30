package com.hotel.dto;

import java.util.Objects;

public class HotelViewModel {

    private Long id;
    private String name;
    private String description;
    private Integer stars;
    private Float avgScore;
    private String country;
    private String city;
    private String url;

    public HotelViewModel() {

    }

    public HotelViewModel(Long id, String name, String description, Integer stars, Float avgScore, String country, String city, String url) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stars = stars;
        this.avgScore = avgScore;
        this.country = country;
        this.city = city;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Float getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(Float avgScore) {
        this.avgScore = avgScore;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HotelViewModel that = (HotelViewModel) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(stars, that.stars) &&
                Objects.equals(avgScore, that.avgScore) &&
                Objects.equals(country, that.country) &&
                Objects.equals(city, that.city) &&
                Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, stars, avgScore, country, city, url);
    }

    @Override
    public String toString() {
        return "HotelViewModel{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", stars=" + stars +
                ", avgScore=" + avgScore +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
