package com.ctl.gr8tparties.controller;


import com.ctl.gr8tparties.dao.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("unit-test")
public class FriendRestControllerTest {

    @Autowired
    protected MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @After
    public void tearDown() throws Exception {
        userRepository.deleteAll();
    }

    @Test
    public void testCreate() throws Exception {
        var json = "{\n" +
                "  \"username\" : \"mark\",\n" +
                "  \"firstName\" : \"Mark\",\n" +
                "  \"lastName\" : \"Knopfler\"\n" +
                "}";

        mvc.perform(post("/friends")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", not(isEmptyOrNullString())))
                .andExpect(jsonPath("$.username", is("mark")))
                .andExpect(jsonPath("$.firstName", is("Mark")))
                .andExpect(jsonPath("$.lastName", is("Knopfler")))
        ;

        mvc.perform(get("/friends").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users", hasSize(1)))
                .andExpect(jsonPath("$.users[0].username", is("mark")))
                .andExpect(jsonPath("$.users[0].firstName", is("Mark")))
                .andExpect(jsonPath("$.users[0].lastName", is("Knopfler")));

    }
}