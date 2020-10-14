package org.revo.charity.service;

import org.revo.charity.controller.filter.vm.SearchCriteria;
import org.revo.charity.domain.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    Optional<User> findOne(Long id);

    Optional<User> findByEmail(String email);

    User save(User user);

    Page<User> findAll(SearchCriteria searchCriteria);
}
