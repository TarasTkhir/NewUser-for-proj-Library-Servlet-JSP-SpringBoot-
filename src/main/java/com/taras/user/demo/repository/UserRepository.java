package com.taras.user.demo.repository;

import com.taras.user.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findByLogin(String login);

    User findByEmailIgnoreCase(String email);
}
