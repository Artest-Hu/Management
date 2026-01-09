package com.example.demo.service;


import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    @Transactional(rollbackFor = UserAgeInvalidException.class)
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id).orElse(null);
        if(userDetails.getAge() < 0){
          throw new UserAgeInvalidException(userDetails.getAge());
        }
        if (user != null) {
            user.setName(userDetails.getName());
            user.setAge(userDetails.getAge());
            user.setSalary(userDetails.getSalary());
            return userRepository.save(user);
        }
        return null;
    }

    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)){
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

}
