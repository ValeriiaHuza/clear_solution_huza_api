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
public class UserServiceImpl implements UserService{

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
    public User update(User newUser, int id) {

        User updatedUser = findById(id);

        StringBuilder errorValidateMessage = new StringBuilder();

        if (newUser.getFirstName()!=null){
            if(newUser.getFirstName().isBlank()){
                errorValidateMessage.append("firstName : First name cannot be blank + \n");
            }
            else {
                updatedUser.setFirstName(newUser.getFirstName());
            }
        }

        if (newUser.getLastName()!=null){
            if(newUser.getLastName().isBlank()){
                errorValidateMessage.append("lastName : Last name cannot be blank + \n");
            }
            else {
                updatedUser.setLastName(newUser.getLastName());
            }
        }

        if(newUser.getEmail()!=null){
            if(newUser.getEmail().isBlank() || !newUser.getEmail().matches("^[\\w-._]+@([\\w-]+\\.)+[\\w-]{2,4}$")){
                errorValidateMessage.append("email : Email invalid + \n");
            }
            else {
                updatedUser.setEmail(newUser.getEmail());
            }
        }

        if(newUser.getBirthDate()!=null){
            LocalDate currentDate = LocalDate.now();
            Period period = Period.between(newUser.getBirthDate(), currentDate);

            if(period.getYears() < VALID_AGE){
                errorValidateMessage.append("birthDate : User must be at least ").append(VALID_AGE).append(" years old \n");
            }
            else {
                updatedUser.setBirthDate(newUser.getBirthDate());
            }
        }

        if(!errorValidateMessage.isEmpty()){
            throw new RuntimeException(errorValidateMessage.toString());
        }

        if(newUser.getAddress()!=null){
            updatedUser.setAddress(newUser.getAddress());
        }

        if(newUser.getPhoneNumber()!=null){
            updatedUser.setPhoneNumber(newUser.getPhoneNumber());
        }

        return userRepository.save(updatedUser);
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

}
