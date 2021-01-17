package ru.mail.dtraider.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.mail.dtraider.crud.model.AuthGroup;
import ru.mail.dtraider.crud.model.User;
import ru.mail.dtraider.crud.service.UserService;

import java.util.LinkedList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/login")
    public ModelAndView getLogin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/")
    public ModelAndView getBack() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }
}
