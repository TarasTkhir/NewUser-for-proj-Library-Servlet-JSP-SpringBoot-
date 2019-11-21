package com.taras.user.demo.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.RequestDispatcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class ErrorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void givenErrorMappingWith404StatusCode_whenPrint_thenReturnErrorPageMappingThatEqualsToStatusCode() throws Exception {

        this.mockMvc.perform(post("/error").requestAttr(RequestDispatcher.ERROR_STATUS_CODE,404))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Language","en"))
                .andExpect(header().string("Content-Type","text/html;charset=UTF-8"))
                .andExpect(view().name("/errors/404.html"));

    }
    @Test
    public void givenErrorMappingWith400StatusCode_whenPrint_thenReturnErrorPageMappingThatEqualsToStatusCode() throws Exception {

        this.mockMvc.perform(post("/error").requestAttr(RequestDispatcher.ERROR_STATUS_CODE,400))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Language","en"))
                .andExpect(header().string("Content-Type","text/html;charset=UTF-8"))
                .andExpect(view().name("/errors/400.html"));

    }
    @Test
    public void givenErrorMappingWith403StatusCode_whenPrint_thenReturnErrorPageMappingThatEqualsToStatusCode() throws Exception {

        this.mockMvc.perform(post("/error").requestAttr(RequestDispatcher.ERROR_STATUS_CODE,403))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Language","en"))
                .andExpect(header().string("Content-Type","text/html;charset=UTF-8"))
                .andExpect(view().name("/errors/403.html"));

    }
    @Test
    public void givenErrorMappingWith500StatusCode_whenPrint_thenReturnErrorPageMappingThatEqualsToStatusCode() throws Exception {

        this.mockMvc.perform(post("/error").requestAttr(RequestDispatcher.ERROR_STATUS_CODE,500))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Language","en"))
                .andExpect(header().string("Content-Type","text/html;charset=UTF-8"))
                .andExpect(view().name("/errors/500.html"));

    }
    @Test
    public void givenErrorMappingWith402StatusCode_whenPrint_thenReturnDefaultErrorPageMapping() throws Exception {

        this.mockMvc.perform(post("/error").requestAttr(RequestDispatcher.ERROR_STATUS_CODE,402))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Language","en"))
                .andExpect(header().string("Content-Type","text/html;charset=UTF-8"))
                .andExpect(view().name("/errors/404.html"));
    }
}