package com.backend.irire.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.irire.Model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {


    User findById(Long id);
    Optional<User> findByEmail(String email);
}