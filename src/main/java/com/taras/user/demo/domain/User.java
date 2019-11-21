package com.taras.user.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private int id;

    @Column(name = "users_first_name")
    private String firstName;

    @Column(name = "users_last_name")
    private String lastName;

    @Column(name = "users_status")
    private boolean isUserActive;

    @Column(name = "users_birth_date")
    private LocalDate birthDay;

    @JsonIgnore
    @Column(name = "users_password")
    private String password;

    @Column(name = "users_login")
    private String login;

    @Column(name = "email")
    private String email;

    @Column(name = "users_role")
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    public enum Role {
        USER;
    }

    public User(String firstName, String lastName, boolean isUserActive, LocalDate birthDay, String password, String login, String email, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isUserActive = isUserActive;
        this.birthDay = birthDay;
        this.password = password;
        this.login = login;
        this.email = email;
        this.role = Role.USER;
    }



    public User(String firstName, String lastName, boolean isUserActive, LocalDate birthDay, String password, String login, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isUserActive = isUserActive;
        this.birthDay = birthDay;
        this.password = password;
        this.login = login;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId() == user.getId() &&
                isUserActive() == user.isUserActive() &&
                Objects.equals(getFirstName(), user.getFirstName()) &&
                Objects.equals(getLastName(), user.getLastName()) &&
                Objects.equals(getBirthDay(), user.getBirthDay()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                getLogin().equals(user.getLogin()) &&
                getEmail().equals(user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), isUserActive(), getBirthDay(), getPassword(), getLogin(), getEmail());
    }
}
