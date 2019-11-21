package com.taras.user.demo.validator;

import com.taras.user.demo.domain.User;
import com.taras.user.demo.form.AppUserForm;
import com.taras.user.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.apache.commons.validator.routines.EmailValidator;

import java.time.DateTimeException;
import java.time.LocalDate;

@Component
public class AppUserValidator implements Validator {

    private EmailValidator emailValidator = EmailValidator.getInstance();

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass == AppUserForm.class;
    }

    @Override
    public void validate(Object o, Errors errors) {
        AppUserForm appUserForm = (AppUserForm) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "NotEmpty.appUserForm.login");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.appUserForm.firstName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty.appUserForm.lastName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.appUserForm.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.appUserForm.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty.appUserForm.confirmPassword");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "birthDate", "Pattern.appUserForm.date");


        if (!this.emailValidator.isValid(appUserForm.getEmail())) {
            // Invalid email.
            errors.rejectValue("email", "Pattern.appUserForm.email");
        } else {
            User dbUser = userRepository.findByEmailIgnoreCase(appUserForm.getEmail());
            if (dbUser != null) {
                // Email has been used by another account.
                errors.rejectValue("email", "Duplicate.appUserForm.email");
            }
        }

        if (!errors.hasFieldErrors("login")) {
            User dbUser = userRepository.findByLogin(appUserForm.getLogin());
            if (dbUser != null) {
                // Username is not available.
                errors.rejectValue("login", "Duplicate.appUserForm.login");
            }
        }
        if (!errors.hasFieldErrors("birthDate")) {
            try {
                LocalDate date = LocalDate.parse(appUserForm.getBirthDate());
            } catch (DateTimeException e) {
                errors.rejectValue("birthDate", "Pattern.appUserForm.date");
            }
        }

        if (!errors.hasErrors()) {
            if (!appUserForm.getConfirmPassword().equals(appUserForm.getPassword())) {
                errors.rejectValue("confirmPassword", "Match.appUserForm.confirmPassword");
            }
        }
    }
}
