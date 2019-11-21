package com.hotel.entities;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "users")
public class UserEntity {

    @Id
    @Column
    @NotNull
    @SequenceGenerator(name = "seq_users", sequenceName = "seq_users", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_users")
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Transient
    private List<GrantedAuthority> grantedAuthorityCollection = new ArrayList<>();

    public UserEntity() {

    }

    public UserEntity(String username, String password, List<GrantedAuthority> grantedAuthorityCollection) {
        this.username = username;
        this.password = password;
        this.grantedAuthorityCollection = grantedAuthorityCollection;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<GrantedAuthority> getGrantedAuthorityCollection() {
        return grantedAuthorityCollection;
    }

    public void setGrantedAuthorityCollection(List<GrantedAuthority> grantedAuthorityCollection) {
        this.grantedAuthorityCollection = grantedAuthorityCollection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity userEntity = (UserEntity) o;
        return Objects.equals(username, userEntity.username) &&
                Objects.equals(password, userEntity.password) &&
                Objects.equals(grantedAuthorityCollection, userEntity.grantedAuthorityCollection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, grantedAuthorityCollection);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", grantedAuthorityCollection=" + grantedAuthorityCollection +
                '}';
    }

    public Long getId() {
        return id;
    }
}
