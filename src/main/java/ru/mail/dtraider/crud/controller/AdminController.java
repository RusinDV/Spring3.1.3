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

    @PostMapping(value = "/add")
    public ModelAndView getAdd(@ModelAttribute("user") User theUser, @ModelAttribute("role") String role) {
        ModelAndView modelAndView = new ModelAndView();

        List<AuthGroup> authGroupListNewUser = new LinkedList<>();

        AuthGroup authGroupUser = new AuthGroup();
        authGroupUser.setName(theUser.getEmail());
        authGroupUser.setAuthGroup("USER");
        authGroupListNewUser.add(authGroupUser);

        if (role.equals("admin")) {
            AuthGroup authGroupAdmin = new AuthGroup();
            authGroupAdmin.setName(theUser.getEmail());
            authGroupAdmin.setAuthGroup("ADMIN");
            authGroupListNewUser.add(authGroupAdmin);
        }
        theUser.setPassword(bCryptPasswordEncoder.encode(theUser.getPassword()));
        theUser.setAuthGroupList(authGroupListNewUser);
        userService.createUser(theUser);
        modelAndView.setViewName("redirect:/admin/");
        return modelAndView;
    }

    @PostMapping(value = "/delete")
    public ModelAndView getDelete(@ModelAttribute("newUser") User theUser) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/");
        userService.deleteUser(theUser.getId());
        return modelAndView;
    }


    @PostMapping(value = "update")
    public ModelAndView getUpdatePost(@ModelAttribute("newUser") User theUser, @ModelAttribute("role") String role) {
        ModelAndView modelAndView = new ModelAndView();

        User userOld = userService.readUser(theUser.getId());

        List<AuthGroup> authGroupListUpdateUser = new LinkedList<>();

        AuthGroup authGroupUser = new AuthGroup();
        authGroupUser.setName(theUser.getEmail());
        authGroupUser.setAuthGroup("USER");
        authGroupListUpdateUser.add(authGroupUser);

        if (role.equals("admin")) {
            AuthGroup authGroupAdmin = new AuthGroup();
            authGroupAdmin.setName(theUser.getEmail());
            authGroupAdmin.setAuthGroup("ADMIN");
            authGroupListUpdateUser.add(authGroupAdmin);
        }

        String encode = bCryptPasswordEncoder.encode(theUser.getPassword());

        userService.updateUser(theUser.getId(), theUser.getFirstName(), theUser.getLastName(), theUser.getAge(), theUser.getEmail(), encode, authGroupListUpdateUser);

        modelAndView.setViewName("redirect:/admin/");
        return modelAndView;
    }

}