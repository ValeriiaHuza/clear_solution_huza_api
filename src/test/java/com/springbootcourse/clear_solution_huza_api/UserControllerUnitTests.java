package com.springbootcourse.clear_solution_huza_api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.springbootcourse.clear_solution_huza_api.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerUnitTests {
    @Autowired
    private MockMvc mockMvc;

    private User addedUser;

    private ObjectMapper om;

    @BeforeAll
    public void setUpMapper() {
        om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
    }

    @BeforeAll
    public void addUser() throws Exception {
        String userJsonTest = """
                {"firstName": "Mariia",
                    "lastName": "Huza",
                    "email": "mariia@gmail.com",
                    "birthDate": "2001-01-01"
                }""";

        MvcResult resultCreate = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJsonTest))
                .andExpect(status().isCreated())
                .andReturn();

        String userString = resultCreate.getResponse().getContentAsString();

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());

        addedUser = om.readValue(userString, User.class);
    }

    @Test
    void getAllUsersTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String userString = result.getResponse().getContentAsString();
        List<User> users = om.readValue(userString, new TypeReference<>() {});

        assertThat(users.contains(addedUser));
    }

    @Test
    public void getUserByIdTest() throws Exception {

        ResultActions result = mockMvc.perform(get("/api/users/{id}", addedUser.getId()));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getUserByIdTestUnsuccessful() throws Exception {

        ResultActions result = mockMvc.perform(get("/api/users/{id}", 0));

        // Assert
        result.andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createUserTest() throws Exception {
        // Arrange
        String userJson = """
                {"firstName": "Valeriia",
                    "lastName": "Huza",
                    "email": "valerie@gmail.com",
                    "birthDate": "2001-01-01"
                }""";

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());
    }

    @Test
    public void createUserUnsuccessfulTest() throws Exception {
        // Arrange
        String userJson = """
                {"firstName": "Valeriia",
                    "email": "valerie@com",
                    "birthDate": "2024-01-01"
                }""";

        // Act
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateUserTestSuccess() throws Exception {

        String userJson = """
                {"firstName": "Mariia",
                    "lastName": "Frost",
                    "email": "mariia-m@gmail.com",
                    "birthDate": "2002-12-02"
                }""";

        // Act
        MvcResult resultCreate = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andReturn();

        String userString = resultCreate.getResponse().getContentAsString();

        User user = om.readValue(userString, User.class);

        String newName = "Valeriia";
        String newEmail = "valeriia.email@example.com";
        String updatedUserJson = "{\"firstName\":\"" + newName + "\",\"email\":\"" + newEmail + "\"}";


        ResultActions result = mockMvc.perform(put("/api/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedUserJson));


        // Assert
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.firstName").value(newName))
                .andExpect(jsonPath("$.email").value(newEmail));
    }

    @Test
    public void updateUserTestUnsuccessful() throws Exception {
        String newName = "Valeriia";
        String newEmail = "valeriia.email@";
        String updatedUserJson = "{\"firstName\":\"" + newName + "\",\"email\":\"" + newEmail + "\"}";


        ResultActions result = mockMvc.perform(put("/api/users/{id}", addedUser.getId())
                .content(updatedUserJson));


        // Assert
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void deleteUserTest() throws Exception {

        String userJson = """
                {"firstName": "Mariia",
                    "lastName": "Huza",
                    "email": "mariia@gmail.com",
                    "birthDate": "2001-01-01"
                }""";

        MvcResult resultCreate = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andReturn();

        String userString = resultCreate.getResponse().getContentAsString();

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());

        User user = om.readValue(userString, User.class);

        ResultActions result = mockMvc.perform(delete("/api/users/{id}", user.getId()));

        // Assert
        result.andExpect(status().isOk());
    }

    @Test
    void getUsersInRangeTestSuccess() throws Exception {
        String from = "2000-10-01";
        String to = "2001-10-01";
        MvcResult result = mockMvc.perform(get("/api/users/range?from=" + from + "&to=" + to ))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String userString = result.getResponse().getContentAsString();
        List<User> users = om.readValue(userString, new TypeReference<>() {});

        assertThat(users.contains(addedUser));

    }

    @Test
    void getUsersInRangeTestUnsuccessful() throws Exception {
        String from = "2002-10-01";
        String to = "2003-10-01";
        MvcResult result = mockMvc.perform(get("/api/users/range?from=" + from + "&to=" + to ))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String userString = result.getResponse().getContentAsString();
        List<User> users = om.readValue(userString, new TypeReference<>() {});

        assertThat(users.contains(addedUser));

    }


}
