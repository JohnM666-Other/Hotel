package com.hotel.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity(name = "roles")
public class Role {

    @Id
    @Column
    @NotNull
    @SequenceGenerator(name = "seq_roles", sequenceName = "seq_roles", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_roles")
    private Long id;

    @Column
    private String role;

    @ManyToOne
    private UserEntity user;

    public Role(String role, UserEntity user) {
        this.role = role;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role1 = (Role) o;
        return Objects.equals(id, role1.id) &&
                Objects.equals(role, role1.role) &&
                Objects.equals(user, role1.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, user);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", user=" + user +
                '}';
    }
}
