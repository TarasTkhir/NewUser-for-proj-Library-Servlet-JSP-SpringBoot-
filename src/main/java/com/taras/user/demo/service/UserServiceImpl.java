package com.taras.user.demo.service;

import com.taras.user.demo.domain.ConfirmationToken;
import com.taras.user.demo.domain.User;
import com.taras.user.demo.form.AppUserForm;
import com.taras.user.demo.repository.ConfirmactionTokenRepository;
import com.taras.user.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.DateTimeException;
import java.time.LocalDate;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;


    private ConfirmactionTokenRepository confirmationTokenRepository;


    private EmailSenderService emailSenderService;

    @Autowired
    public void setConfirmationTokenRepository(ConfirmactionTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @Autowired
    public void setEmailSenderService(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String createNewUser(Model model, AppUserForm appUserForm,
                                BindingResult result, final RedirectAttributes redirectAttributes) {
        // Validate result
        if (result.hasErrors()) {

            return "registerPage.html";
        }
        User newUser = null;
        LocalDate userDate = null;
        try {
            userDate = LocalDate.parse(appUserForm.getBirthDate());

            newUser = userRepository.save(new User(appUserForm.getFirstName(), appUserForm.getLastName(),
                    false, userDate, appUserForm.getPassword(), appUserForm.getLogin(), appUserForm.getEmail()));
            ConfirmationToken confirmationToken = new ConfirmationToken(newUser);
            confirmationTokenRepository.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(newUser.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("nazar.yaremch@gmail.com");
            mailMessage.setText("To confirm your account, please click here : "
                    + "http://localhost:8080/user/confirm-account?token=" + confirmationToken.getConfirmationToken());

            emailSenderService.sendEmail(mailMessage);

            model.addAttribute("email", newUser.getEmail());

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Error!");
            return "registerPage";
        }

        redirectAttributes.addFlashAttribute("flashUser", newUser);

        return "redirect:/registerSuccessful";
    }

    public String endOfRegistration(Model model, String confirmationToken) {

        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if (token != null) {
            User user = userRepository.findByEmailIgnoreCase(token.getUser().getEmail());
            user.setUserActive(true);
            User save = userRepository.save(user);
            model.addAttribute("flashUser", save);
            confirmationTokenRepository.delete(token);
            return "registrationComplited.html";
        } else {
            model.addAttribute("message", "The link is invalid or broken!");
            return ("errorBrokenToken.html");
        }

    }
}
