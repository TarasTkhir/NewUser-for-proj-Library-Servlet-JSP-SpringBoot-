package com.taras.user.demo.controller;

import TestData.CreateTestData;
import com.taras.user.demo.form.AppUserForm;
import com.taras.user.demo.repository.ConfirmactionTokenRepository;
import com.taras.user.demo.repository.UserRepository;
import com.taras.user.demo.service.EmailSenderService;
import com.taras.user.demo.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ConfirmactionTokenRepository confirmactionTokenRepository;

    @Autowired
    UserService userService;

    @MockBean
    EmailSenderService emailSenderService;

    @Before
    public void setUp() throws Exception {
        CreateTestData.generateTestUsers(userRepository);
        CreateTestData.generateTestToken(confirmactionTokenRepository, userRepository);
    }

    @After
    public void tearDown() throws Exception {
        confirmactionTokenRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void givenMockMvc_whenPostRootMapping_thenReturnRegistrationPageForUser() throws Exception {

        this.mockMvc.perform(post("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("registerPage.html"))
                .andExpect(model().attributeExists("appUserForm"));
    }

    @Test
    public void givenMockMvc_whenPostCreateUserWithValidUserAttribute_thenReturnRedirectToSuccessRegistrationPage() throws Exception {

        AppUserForm appUserForm =
                new AppUserForm("TEST", "TEST", "1212-12-12", "TESTMAIL@gmail.com", "TEST", "TEST", "TEST");

        this.mockMvc.perform(post("/user/create").flashAttr("appUserForm", appUserForm))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(header().exists("Location"))
                .andExpect(flash().attributeExists("flashUser"))
                .andExpect(view().name("redirect:/registerSuccessful"))
                .andExpect(redirectedUrl("/registerSuccessful"));
    }

    @Test
    public void givenMockMvc_whenPostCreateUserWithInvalidUserAttributesLoginAndEmail_thenReturnBackRegistrationPageForUserWithErrors() throws Exception {

        AppUserForm appUserForm =
                new AppUserForm("TEST", "TEST", "1212-12-12", "", "", "TEST", "TEST");

        this.mockMvc.perform(post("/user/create").flashAttr("appUserForm", appUserForm))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attributeExists("appUserForm"))
                .andExpect(view().name("registerPage.html"))
                .andExpect(model().hasErrors())
                .andExpect(model().errorCount(3));
    }

    @Test
    public void givenMockMvc_whenPostCreateUserWithInvalidDateInUserAttribute_thenReturnBackRegistrationPageForUserWithError() throws Exception {

        AppUserForm appUserForm =
                new AppUserForm("TEST", "TEST", "221222-12-12", "TESTMAIL@gmail.com", "TEST", "TEST", "TEST");

        this.mockMvc.perform(post("/user/create").flashAttr("appUserForm", appUserForm))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attributeExists("appUserForm"))
                .andExpect(view().name("registerPage.html"))
                .andExpect(model().hasErrors())
                .andExpect(model().errorCount(1));
    }

    @Test
    public void givenMockMvc_whenPostCreateUserWithEmptyUser_thenReturnBackRegistrationPageForUser() throws Exception {

        AppUserForm appUserForm =
                new AppUserForm("", "", "", "", "", "", "");

        this.mockMvc.perform(post("/user/create").flashAttr("appUserForm", appUserForm))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attributeExists("appUserForm"))
                .andExpect(view().name("registerPage.html"))
                .andExpect(model().hasErrors())
                .andExpect(model().errorCount(8));
    }

    @Test
    public void givenMockMvc_whenPostConfirmAccountMappingWithWrongToken_thenReturnTokenIsBroken() throws Exception {

        this.mockMvc.perform(post("/confirm-account?token=this_token_is_not_exist_in_db"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("errorBrokenToken.html"));
    }

    @Test
    public void givenMockMvc_whenPostConfirmAccountMappingWithExistingToken_thenReturnRegisterSuccessfulPage() throws Exception {

        this.mockMvc.perform(post("/confirm-account?token=f7c7081a-b38e-11e9-a2a3-2a2ae2dbcce4"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attributeExists("flashUser"))
                .andExpect(view().name("registrationComplited.html"));
    }
}