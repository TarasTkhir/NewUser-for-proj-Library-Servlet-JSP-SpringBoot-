package com.taras.user.demo.repository;

import TestData.CreateTestData;
import com.taras.user.demo.domain.ConfirmationToken;
import com.taras.user.demo.domain.User;
import com.taras.user.demo.service.EmailSenderService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@DataJpaTest
public class ConfirmactionTokenRepositoryTest {

    @Autowired
    ConfirmactionTokenRepository confirmactionTokenRepository;

    @Autowired
    UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        CreateTestData.generateTestUsers(userRepository);
        CreateTestData.generateTestToken(confirmactionTokenRepository,userRepository);
    }

    @After
    public void tearDown() throws Exception {
        confirmactionTokenRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void givenTestData_wenFindTokenByTokenCalled_thenReturnTokenObjectWithUser() {
        //given
        User testUnit = new User(1, "Vasua", "Pupkin",
                false, LocalDate.parse("1212-12-12"), "root", "root", "root@gmail.com", User.Role.USER);
        //when
        ConfirmationToken byConfirmationToken = confirmactionTokenRepository.findByConfirmationToken("f7c7081a-b38e-11e9-a2a3-2a2ae2dbcce4");
        //then
        assertEquals(testUnit, byConfirmationToken.getUser());
        assertEquals("f7c7081a-b38e-11e9-a2a3-2a2ae2dbcce4", byConfirmationToken.getConfirmationToken());
        assertEquals(1, byConfirmationToken.getTokenid());
    }
}