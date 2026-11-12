
package com.example.petmatch_lab;

import com.example.petmatch_lab.model.Pet;
import com.example.petmatch_lab.repository.PetRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private PetRepository petRepository;

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
    @Transactional
    void petDetailShouldWork() throws Exception {

        Pet pet = new Pet(
                null,
                "Mascota de prueba",
                "Perro",
                2,
                "Mascota creada para la prueba automatizada"
        );

        Pet savedPet = petRepository.save(pet);

        mockMvc.perform(get("/pets/" + savedPet.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/detail"))
                .andExpect(model().attributeExists("pet"));
    }

    @Test
    void missingPetShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/pets/9223372036854775807"))
                .andExpect(status().isNotFound());
    }
}
