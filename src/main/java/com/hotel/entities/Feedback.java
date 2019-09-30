package com.hotel.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity(name = "feedbacks")
public class Feedback {

    @Id
    @Column
    @NotNull
    @SequenceGenerator(name = "seq_feedbacks", sequenceName = "seq_feedbacks", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_feedbacks")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column
    @NotNull
    private Date visitDate;

    @Column
    @NotNull
    private Integer score;

    @Column
    private String text;

    public Feedback() {

    }

    public Feedback(Hotel hotelId, Customer customerId, Date visitDate, Integer score, String text) {
        this.hotel = hotelId;
        this.customer = customerId;
        this.visitDate = visitDate;
        this.score = score;
        this.text = text;
    }

    public Long getId() {
        return id;
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

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", hotel=" + hotel +
                ", customer=" + customer +
                ", visitDate=" + visitDate +
                ", score=" + score +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(id, feedback.id) &&
                Objects.equals(hotel, feedback.hotel) &&
                Objects.equals(customer, feedback.customer) &&
                Objects.equals(visitDate, feedback.visitDate) &&
                Objects.equals(score, feedback.score) &&
                Objects.equals(text, feedback.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hotel, customer, visitDate, score, text);
    }
}
