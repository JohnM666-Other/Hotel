package com.hotel.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity(name = "customers")
public class Customer {

    @Id
    @NotNull
    @Column
    @SequenceGenerator(name = "seq_customers", sequenceName = "seq_customers", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_customers")
    private Long id;

    @Column
    @NotNull
    private String firstname;

    @Column
    @NotNull
    private String secondname;

    @Column
    private String email;

    @Column
    private Date birthDate;

    @Column
    @NotNull
    private Character sex;

    @OneToMany
    @JsonIgnore
    public List<Feedback> feedbacks;

    public Customer() {

    }

    public Customer(String firstname, String secondname, String email, Date birthDate, Character sex) {
        this.firstname = firstname;
        this.secondname = secondname;
        this.email = email;
        this.birthDate = birthDate;
        this.sex = sex;
    }

    public Long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Character getSex() {
        return sex;
    }

    public void setSex(Character sex) {
        this.sex = sex;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", secondname='" + secondname + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                ", sex=" + sex +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) &&
                Objects.equals(firstname, customer.firstname) &&
                Objects.equals(secondname, customer.secondname) &&
                Objects.equals(email, customer.email) &&
                Objects.equals(birthDate, customer.birthDate) &&
                Objects.equals(sex, customer.sex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, secondname, email, birthDate, sex);
    }
}
