package ru.mail.dtraider.crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.dtraider.crud.dao.UserDao;
import ru.mail.dtraider.crud.model.AuthGroup;
import ru.mail.dtraider.crud.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public void createUser(User user) {
        userDao.createUser(user);
    }

    @Transactional (readOnly = true)
    @Override
    public User readUser(Long idUser) {
        return userDao.readUser(idUser);
    }

    @Transactional(readOnly = true)
    @Override
    public User readUserByEmail(String email) {
        return userDao.readUserByEmail(email);
    }

    @Transactional
    @Override
    public void updateUser(Long idUser, String firstName, String lastName, int age, String email, String password, List<AuthGroup> authGroupList) {
        userDao.updateUser(idUser, firstName, lastName, age, email, password, authGroupList);
    }

    @Transactional
    @Override
    public void deleteUser(Long idUser) {
        userDao.deleteUser(idUser);
    }

    @Transactional
    @Override
    public List<User> getUsers() {
        return userDao.getUsers();
    }
}
