package com.springbootcourse.clear_solution_huza_api.repository;

import com.springbootcourse.clear_solution_huza_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {


    @Query("select u from User u where u.birthDate between ?1 and ?2")
    List<User> findByBirthDateBetween(LocalDate fromDate, LocalDate toDate);;
}