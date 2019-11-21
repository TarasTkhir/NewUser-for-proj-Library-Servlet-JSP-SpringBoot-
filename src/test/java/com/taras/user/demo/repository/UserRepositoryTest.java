package com.taras.user.demo.repository;

import TestData.CreateTestData;
import com.taras.user.demo.domain.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Before
    public void setUp() throws Exception {

        CreateTestData.generateTestUsers(userRepository);
    }

    @After
    public void tearDown() throws Exception {

        userRepository.deleteAll();
    }

    @Test
    public void givenTestData_wenFindUserByEmailCalledWithExistingEmail_thenReturnUser() {
        //given
        //when
        User byEmailIgnoreCase = userRepository.findByEmailIgnoreCase("root@gmail.com");
        //then
        assertEquals("root@gmail.com",byEmailIgnoreCase.getEmail());
        assertEquals("root",byEmailIgnoreCase.getLogin());
        assertEquals("Vasua",byEmailIgnoreCase.getFirstName());
        assertEquals("Pupkin",byEmailIgnoreCase.getLastName());

    }

    @Test
    public void givenTestData_wenFindUserByEmailCalledWithNotExistingEmail_thenReturnNullUser() {
        //given
        //when
        User byEmailIgnoreCase = userRepository.findByEmailIgnoreCase("notExistingEmail@com.ua");
        //then
        assertEquals(null,byEmailIgnoreCase);

    }

    @Test
    public void givenTestData_wenFindUserByLoginCalled_thenReturnUser() {
        //given
        //when
        User byLogin = userRepository.findByLogin("root");
        //then
        assertEquals("root",byLogin.getLogin());
        assertEquals("root@gmail.com",byLogin.getEmail());
        assertEquals("Vasua",byLogin.getFirstName());
        assertEquals("Pupkin",byLogin.getLastName());
    }

    @Test
    public void givenTestData_wenFindUserByLoginCalledWithNotExistingLogin_thenReturnNullUser() {
        //given
        //when
        User byLogin = userRepository.findByLogin("thisLoginNotExist");
        //then
        assertEquals(null,byLogin);

    }
}