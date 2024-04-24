package com.springbootcourse.clear_solution_huza_api.repository;

import com.springbootcourse.clear_solution_huza_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}