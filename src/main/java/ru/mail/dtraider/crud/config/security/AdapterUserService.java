package ru.mail.dtraider.crud.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mail.dtraider.crud.model.AuthGroup;
import ru.mail.dtraider.crud.model.User;
import ru.mail.dtraider.crud.service.UserService;

import java.util.List;

@Service
public class AdapterUserService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.readUserByEmail(s);
        if (user == null) {
            throw new UsernameNotFoundException("cannot find user " + s);
        }
        List<AuthGroup> authGroupList= user.getAuthGroupList();
        return new AdapterUserDetails(user,authGroupList);
    }
}
