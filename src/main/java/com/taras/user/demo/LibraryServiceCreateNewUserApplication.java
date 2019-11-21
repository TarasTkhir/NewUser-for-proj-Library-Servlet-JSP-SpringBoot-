package com.taras.user.demo;

import com.taras.user.demo.domain.User;
import com.taras.user.demo.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class    LibraryServiceCreateNewUserApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(LibraryServiceCreateNewUserApplication.class, args);
        UserRepository userRepository = (UserRepository)run.getBean("userRepository");
        userRepository.save(new User("Vasua","Pupkin",false, LocalDate.parse("1212-12-12"),"root","root1234123213213","root@gmail.com", User.Role.USER));
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(LibraryServiceCreateNewUserApplication.class);
    }

}
