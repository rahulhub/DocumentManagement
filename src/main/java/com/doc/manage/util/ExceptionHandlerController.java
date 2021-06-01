package com.doc.manage.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import com.doc.manage.entity.ApiResponse;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ApiResponse<String> handleError(HttpServletRequest req, Exception ex) {
        System.out.println(req.getRequestURL());
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("msg", "Sorry, we couldn't find what you are looking for." + ex.getMessage());
//        mav.addObject("url", req.getRequestURL());
//        mav.setViewName("error");
        return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Exception Occured",ex.getMessage());
    }
}