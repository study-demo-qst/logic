package com.example.logic.service;

import com.example.common.model.User;
import com.example.common.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findByUsername(String username) {
        List<User> ret = userRepository.findByUsername(username);
        List<User> ret2 = userRepository.findAll();
        System.out.println("------------------- ret2");
        System.out.println(ret2);
        System.out.println("-------------------");
        return ret;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
