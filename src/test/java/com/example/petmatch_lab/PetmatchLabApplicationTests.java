
package com.example.petmatch_lab;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
@AutoConfigureMockMvc
class PetmatchLabApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void homePageShouldWork() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("message"));
    }

    @Test
    void aboutPageShouldWork() throws Exception {
        mockMvc.perform(get("/about"))
                .andExpect(status().isOk())
                .andExpect(view().name("about"));
    }

    @Test
    void petListShouldWork() throws Exception {
        mockMvc.perform(get("/pets"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/list"))
                .andExpect(model().attributeExists("pets"));
    }

    @Test
    void petDetailShouldWork() throws Exception {
        mockMvc.perform(get("/pets/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/detail"))
                .andExpect(model().attributeExists("pet"));
    }

    @Test
    void missingPetShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/pets/999"))
                .andExpect(status().isNotFound());
    }
}
