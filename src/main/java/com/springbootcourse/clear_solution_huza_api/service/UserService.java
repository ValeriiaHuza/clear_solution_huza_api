package com.springbootcourse.clear_solution_huza_api.service;

import com.springbootcourse.clear_solution_huza_api.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(int id);
    User save(User user);
    void deleteById(int id);
}
