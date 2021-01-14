package ru.mail.dtraider.crud.service;


import ru.mail.dtraider.crud.model.AuthGroup;
import ru.mail.dtraider.crud.model.User;

import java.util.List;

public interface UserService {
    void createUser(User user);

    User readUser(Long idUser);

    User readUserByEmail(String email);

    void updateUser(Long idUser, String firstName, String lastName, int age, String email, String password, List<AuthGroup> authGroupList);

    void deleteUser(Long idUser);

    List<User> getUsers();
}
