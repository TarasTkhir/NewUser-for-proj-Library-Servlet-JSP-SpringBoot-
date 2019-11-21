package com.taras.user.demo.form;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AppUserForm {

    private String firstName;
    private String lastName;
    private String birthDate;
    private String email;
    private String login;
    private String password;
    private String confirmPassword;

}
