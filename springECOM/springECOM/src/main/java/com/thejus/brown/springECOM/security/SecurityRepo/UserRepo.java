package com.thejus.brown.springECOM.security.SecurityRepo;

import com.thejus.brown.springECOM.security.SecurityModel.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {
    User findByUsername(String username);
}
