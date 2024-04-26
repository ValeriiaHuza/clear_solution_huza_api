package com.springbootcourse.clear_solution_huza_api.service;

import com.springbootcourse.clear_solution_huza_api.entity.User;
import com.springbootcourse.clear_solution_huza_api.error.UserNotFoundException;
import com.springbootcourse.clear_solution_huza_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Value("${user.validAge}")
    private int VALID_AGE;

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
        Optional<User> res = userRepository.findById(id);

        User user;
        if (res.isPresent()) {
            user = res.get();
        } else {
            throw new UserNotFoundException("User not found for id: " + id);
        }

        return user;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User newUser, int id) {

        User updatedUser = findById(id);

        validateUser(updatedUser, newUser);

        return userRepository.save(updatedUser);
    }

    private void validateUser(User updatedUser, User newUser) {
        StringBuilder errorValidateMessage = new StringBuilder();

        if (newUser.getFirstName() != null) {
            if (newUser.getFirstName().isBlank()) {
                errorValidateMessage.append("firstName : First name can't be blank \n");
            } else {
                updatedUser.setFirstName(newUser.getFirstName());
            }
        }

        if (newUser.getLastName() != null) {
            if (newUser.getLastName().isBlank()) {
                errorValidateMessage.append("lastName : Last name can't be blank \n");
            } else {
                updatedUser.setLastName(newUser.getLastName());
            }
        }

        if (newUser.getEmail() != null) {
            if (newUser.getEmail().isBlank() || !newUser.getEmail().matches("^[\\w-._]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                errorValidateMessage.append("email : Email format isn't correct \n");
            } else {
                updatedUser.setEmail(newUser.getEmail());
            }
        }

        if (newUser.getBirthDate() != null) {
            LocalDate currentDate = LocalDate.now();

             if (newUser.getBirthDate().isAfter(currentDate)){
                 errorValidateMessage.append("birthDate : Date must not be in the future \n");
             }

            Period period = Period.between(newUser.getBirthDate(), currentDate);

            if (period.getYears() < VALID_AGE) {
                errorValidateMessage.append("birthDate : User must be at least ").append(VALID_AGE).append(" years old \n");
            } else {
                updatedUser.setBirthDate(newUser.getBirthDate());
            }
        }

        if (newUser.getAddress() != null) {
            updatedUser.setAddress(newUser.getAddress());
        }

        if (newUser.getPhoneNumber() != null) {
            if (newUser.getPhoneNumber().isBlank() || !newUser.getPhoneNumber().matches("^[0-9\\-\\+]{9,13}$")) {
                errorValidateMessage.append("phoneNumber : Phone format isn't correct \n");
            } else {
                updatedUser.setPhoneNumber(newUser.getPhoneNumber());
            }
        }

        if (!errorValidateMessage.isEmpty()) {
            throw new RuntimeException(errorValidateMessage.toString());
        }
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findUsersByBirthDateRange(LocalDate fromDate, LocalDate toDate) {
        if (fromDate.isAfter(toDate)) {
            throw new RuntimeException("Invalid date range: 'From' must be less than 'To'");
        }
        return userRepository.findByBirthDateBetween(fromDate, toDate);
    }

}
