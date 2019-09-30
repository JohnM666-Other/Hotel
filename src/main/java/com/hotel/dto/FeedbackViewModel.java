package com.hotel.dto;

import com.hotel.entities.Customer;
import com.hotel.entities.Hotel;

import java.util.Date;
import java.util.Objects;

public class FeedbackViewModel {
    private Long id;
    private Hotel hotel;
    private Customer customer;
    private Date visitDate;
    private Integer score;
    private String text;
    private Integer customerAge;

    public FeedbackViewModel() {

    }

    public FeedbackViewModel(Long id, Hotel hotel, Customer customer, Date visitDate, Integer score, String text, Integer customerAge) {
        this.id = id;
        this.hotel = hotel;
        this.customer = customer;
        this.visitDate = visitDate;
        this.score = score;
        this.text = text;
        this.customerAge = customerAge;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getCustomerAge() {
        return customerAge;
    }

    public void setCustomerAge(Integer customerAge) {
        this.customerAge = customerAge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedbackViewModel viewModel = (FeedbackViewModel) o;
        return Objects.equals(id, viewModel.id) &&
                Objects.equals(hotel, viewModel.hotel) &&
                Objects.equals(customer, viewModel.customer) &&
                Objects.equals(visitDate, viewModel.visitDate) &&
                Objects.equals(score, viewModel.score) &&
                Objects.equals(text, viewModel.text) &&
                Objects.equals(customerAge, viewModel.customerAge);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hotel, customer, visitDate, score, text, customerAge);
    }

    @Override
    public String toString() {
        return "FeedbackViewModel{" +
                "id=" + id +
                ", hotel=" + hotel +
                ", customer=" + customer +
                ", visitDate=" + visitDate +
                ", score=" + score +
                ", text='" + text + '\'' +
                ", customerAge=" + customerAge +
                '}';
    }
}
