package com.slapples.repository;

import com.slapples.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String emailId);
    boolean existsByEmail(String emailId);
}
