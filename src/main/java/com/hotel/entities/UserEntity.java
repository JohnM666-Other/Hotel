package com.hotel.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity(name = "users")
public class UserEntity {

    @Id
    @NotNull
    @Column
    @SequenceGenerator(name = "seq_users", sequenceName = "seq_users", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_users")
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

    @Column
    @NotNull
    private String password;

    @OneToMany
    @JsonIgnore
    public List<Feedback> feedbacks;

    public UserEntity() {

    }

    public UserEntity(String firstname, String secondname, String email, Date birthDate, Character sex, String password) {
        this.firstname = firstname;
        this.secondname = secondname;
        this.email = email;
        this.birthDate = birthDate;
        this.sex = sex;
        this.password = password;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
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
        UserEntity userEntity = (UserEntity) o;
        return Objects.equals(id, userEntity.id) &&
                Objects.equals(firstname, userEntity.firstname) &&
                Objects.equals(secondname, userEntity.secondname) &&
                Objects.equals(email, userEntity.email) &&
                Objects.equals(birthDate, userEntity.birthDate) &&
                Objects.equals(sex, userEntity.sex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, secondname, email, birthDate, sex);
    }
}
