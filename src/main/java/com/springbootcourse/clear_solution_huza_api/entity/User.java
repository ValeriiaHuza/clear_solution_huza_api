package com.springbootcourse.clear_solution_huza_api.entity;

import com.springbootcourse.clear_solution_huza_api.utils.UnderAge;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotBlank(message = "First name is required")
    @Size(max = 45, message = "Max first name length is 45")
    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 45, message = "Max last name length is 45")
    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;

    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[\\w-._]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email format isn't correct")
    @Column(name = "email", nullable = false, length = 60)
    private String email;

    @NotNull(message = "Birth date is required")
    @UnderAge()
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "address")
    private String address;

    //TODO: add pattern
    @Column(name = "phone_number", length = 45)
    private String phoneNumber;

    public User(String firstName, String lastName, String email, LocalDate birthDate, String address, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}