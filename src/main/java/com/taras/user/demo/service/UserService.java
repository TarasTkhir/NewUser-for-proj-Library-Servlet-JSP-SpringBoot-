package com.taras.user.demo.service;

import com.taras.user.demo.domain.User;
import com.taras.user.demo.form.AppUserForm;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface UserService {

    String createNewUser(Model model, AppUserForm appUserForm,
                         BindingResult result, final RedirectAttributes redirectAttributes);

    String endOfRegistration(Model model, String confirmationToken);

}
