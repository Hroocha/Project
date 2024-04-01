package com.shopproject.user.service;

import com.shopproject.user.dtos.RegistrationUserDTO;
import com.shopproject.user.entity.User;
import com.shopproject.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private  PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public UUID getIdByLogin(String login) {
        User user = findByLogin(login).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", login)
        ));
        return user.getId();
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByLogin(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", username)
        ));
        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                new ArrayList<>()
        );
    }

    public User createNewUser(RegistrationUserDTO registrationUserDTO) {
        User user = new User();
        user.setLogin(registrationUserDTO.getLogin());
        user.setPassword(passwordEncoder.encode(registrationUserDTO.getPassword()));
        user.setName(registrationUserDTO.getName());
        user.setMail(registrationUserDTO.getMail());
        return userRepository.save(user);
    }
}
