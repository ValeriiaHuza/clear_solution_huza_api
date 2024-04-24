package com.springbootcourse.clear_solution_huza_api.service;

import com.springbootcourse.clear_solution_huza_api.entity.User;
import com.springbootcourse.clear_solution_huza_api.error.UserNotFoundException;
import com.springbootcourse.clear_solution_huza_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int id) {
       Optional<User> res =  userRepository.findById(id);

       User user;
       if(res.isPresent()) {
           user = res.get();
       }
       else {
           throw new UserNotFoundException("User not found for id: " + id);
       }

       return user;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }
}
