package org.revo.charity.controller;

import org.revo.charity.controller.filter.vm.SearchCriteria;
import org.revo.charity.domain.User;
import org.revo.charity.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("filter")
    public ResponseEntity<Page<User>> filter(@RequestBody SearchCriteria searchCriteria) {
        Page<User> all = userService.findAll(searchCriteria);
        return ResponseEntity.ok(all);
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<User>> findOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.findOne(id));
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }
}
