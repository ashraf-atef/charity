package org.revo.charity.service.impl;

import org.revo.charity.controller.filter.SpecificationUtil;
import org.revo.charity.controller.filter.vm.SearchCriteria;
import org.revo.charity.domain.User;
import org.revo.charity.repository.UserRepository;
import org.revo.charity.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    public UserServiceImpl(UserRepository userRepository, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }

    @Override
    public List<User> findAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<User> findOne(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Page<User> findAll(SearchCriteria searchCriteria) {
        return SpecificationUtil.findAllBySpecification(entityManager, searchCriteria, User.class);
    }
}
