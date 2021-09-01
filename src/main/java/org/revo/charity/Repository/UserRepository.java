package org.revo.charity.Repository;

import io.micrometer.core.lang.Nullable;
import org.revo.charity.Domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(@Nullable String email);
    List<User> findListByEmail(@Nullable String email);
    Optional<User> findByPhone(String phone);
}
