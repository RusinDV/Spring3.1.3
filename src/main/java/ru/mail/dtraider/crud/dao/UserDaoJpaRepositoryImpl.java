package ru.mail.dtraider.crud.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.mail.dtraider.crud.model.AuthGroup;
import ru.mail.dtraider.crud.model.User;

import javax.persistence.OrderBy;
import java.util.List;

@Repository
public class UserDaoJpaRepositoryImpl implements UserDao {
    @Autowired
    private UserJpaRepository userJPARepository;

    @Override
    public void createUser(User user) {
        userJPARepository.save(user);
    }

    @Override
    public User readUser(Long idUser) {
        User user = userJPARepository.findById(idUser).get();
        return user;
    }

    @Override
    public User readUserByEmail(String email) {
        return userJPARepository.findByEmail(email);
    }

    @Override
    public void updateUser(Long idUser, String firstName, String lastName, int age, String email, String password, List<AuthGroup> authGroupList) {
        User user = userJPARepository.getOne(idUser);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAge(age);
        user.setEmail(email);
        user.setPassword(password);
        user.setAuthGroupList(authGroupList);
        userJPARepository.save(user);
    }

    @Override
    public void deleteUser(Long idUser) {
        userJPARepository.deleteById(idUser);
    }

    @Override
    public List<User> getUsers() {
        return userJPARepository.findAll(Sort.by("id"));
    }
}
