package ru.mail.dtraider.crud.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mail.dtraider.crud.model.User;

import java.util.List;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
