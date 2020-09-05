package org.revo.charity.Service;

import org.revo.charity.Domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    Optional<User> findOne(Long id);

    User save(User user);
}
