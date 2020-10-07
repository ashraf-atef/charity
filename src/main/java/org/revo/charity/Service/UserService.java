package org.revo.charity.Service;

import org.revo.charity.Domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    Optional<User> findOne(Long id);

    Optional<User> findByEmail(String email);

    User save(User user);

    Optional<User> findRevo();
}
