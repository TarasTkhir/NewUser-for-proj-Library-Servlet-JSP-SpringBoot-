package com.taras.user.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "/errors/404.html";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {

                return "/errors/500.html";
            } else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
                return "/errors/400.html";
            }else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "/errors/403.html";
            }
        }
        return "/errors/404.html";

    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
