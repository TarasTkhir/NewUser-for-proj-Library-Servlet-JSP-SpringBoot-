package com.taras.user.demo.controller;

import com.taras.user.demo.form.AppUserForm;
import com.taras.user.demo.service.UserService;
import com.taras.user.demo.validator.AppUserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    private UserService userService;

    private AppUserValidator appUserValidator;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setAppUserValidator(AppUserValidator appUserValidator) {
        this.appUserValidator = appUserValidator;
    }

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        // Form target
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }

        if (target.getClass() == AppUserForm.class) {
            dataBinder.setValidator(appUserValidator);
        }
    }

    @RequestMapping("/")
    public String registrationPage(Model model) {

        AppUserForm form = new AppUserForm();
        model.addAttribute("appUserForm", form);
        return "registerPage.html";
    }

    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public String registrationForm(Model model, @ModelAttribute("appUserForm") @Validated AppUserForm appUserForm,
                                   BindingResult result, final RedirectAttributes redirectAttributes) {

        return userService.createNewUser(model, appUserForm,
                result, redirectAttributes);
    }

    @RequestMapping("/registerSuccessful")
    public String viewRegisterSuccessful(Model model) {

        return "registerSuccessfulPage";
    }

    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public String confirmUserAccount(Model model, @RequestParam("token") String confirmationToken) {

        return userService.endOfRegistration(model, confirmationToken);
    }

}
