package com.hotel.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity(name = "hotels")
public class Hotel {

    @Id
    @Column
    @NotNull
    @SequenceGenerator(name = "seq_hotels", sequenceName = "seq_hotels", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_hotels")
    private Long id;

    @Column
    @NotNull
    private String name;

    @Column
    private String description;

    @Column
    @NotNull
    private String country;

    @Column
    @NotNull
    private String city;

    @Column
    private String url;

    @Column
    @NotNull
    private Integer score;

    @OneToMany(mappedBy = "hotel")
    @JsonIgnore
    private List<Feedback> feedbacks;

    public Hotel() {

    }

    public Hotel(String name, String description, String county, String city, String url, Integer score) {
        this.name = name;
        this.description = description;
        this.country = county;
        this.city = city;
        this.url = url;
        this.score = score;
    }

    public Long getId() {
        return id;
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", url='" + url + '\'' +
                ", score=" + score +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return Objects.equals(id, hotel.id) &&
                Objects.equals(name, hotel.name) &&
                Objects.equals(description, hotel.description) &&
                Objects.equals(country, hotel.country) &&
                Objects.equals(city, hotel.city) &&
                Objects.equals(url, hotel.url) &&
                Objects.equals(score, hotel.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, country, city, url, score);
    }
}
