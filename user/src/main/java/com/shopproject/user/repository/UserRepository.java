package com.shopproject.user.repository;

import com.shopproject.user.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    Optional<User> findByLogin(String login);

}
