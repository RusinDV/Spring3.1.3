package ru.mail.dtraider.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.mail.dtraider.crud.model.User;
import ru.mail.dtraider.crud.service.UserService;

import java.security.Principal;


@Controller
@PreAuthorize(value = "hasRole('USER')")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ModelAndView getIndex(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user");
        User user = userService.readUserByEmail(principal.getName());
        modelAndView.addObject("user", user);
        return modelAndView;

    }


}
