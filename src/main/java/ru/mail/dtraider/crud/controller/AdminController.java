package ru.mail.dtraider.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.mail.dtraider.crud.model.AuthGroup;
import ru.mail.dtraider.crud.model.User;
import ru.mail.dtraider.crud.service.UserService;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;


@Controller
@RequestMapping("/admin")
@PreAuthorize(value = "hasRole('ADMIN')")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/")
    public ModelAndView getIndex(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();

        User userP = userService.readUserByEmail(principal.getName());
        List<User> allUsers = userService.getUsers();

        modelAndView.addObject("user", userP);

        modelAndView.addObject("newUser", new User());

        modelAndView.addObject("allUsers", allUsers);

        modelAndView.setViewName("admin");

        return modelAndView;
    }



}