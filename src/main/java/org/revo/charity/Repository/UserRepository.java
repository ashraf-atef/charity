package org.revo.charity.Repository;

import org.revo.charity.Domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query(value = "select user from User user where user.email like '%:query%'")
    List<User> selectInArea(@Param("query") String query);

    @Query(value = "select * from r_user where email like :query", nativeQuery =true)
    Optional<User> findRevo(@Param("query") String query);

    List<User> findAllByEmailLike(String query);
}
