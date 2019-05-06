package com.longer.controller;

import com.longer.bean.DPFBean;
import com.longer.bean.DPFResponse;
import com.longer.service.ExecuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RestController
public class LoginController {
    @RequestMapping("/")
    public ModelAndView index(){
        return new ModelAndView("login.html");
    }
}
