
package com.example.petmatch_lab.controller;

import com.example.petmatch_lab.form.PetForm;
import com.example.petmatch_lab.model.Pet;
import com.example.petmatch_lab.service.PetService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    // Mostrar catálogo
    @GetMapping
    public String list(Model model) {
        model.addAttribute("pets", petService.findAll());
        return "pets/list";
    }

    // Mostrar detalles
    @GetMapping("/{id}")
    public String detail(@PathVariable("id") long id, Model model) {
        Pet pet = petService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Pet not found"));

        model.addAttribute("pet", pet);
        return "pets/detail";
    }

    // Mostrar formulario de registro
    @GetMapping("/new")
    public String newPet(Model model) {
        model.addAttribute("petForm", new PetForm());
        model.addAttribute("petId", null);
        return "pets/form";
    }

    // Guardar mascota nueva
    @PostMapping
    public String create(
            @Valid @ModelAttribute("petForm") PetForm form,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("petId", null);
            return "pets/form";
        }

        Pet pet = new Pet(
                null,
                form.getName(),
                form.getSpecies(),
                form.getAge(),
                form.getDescription()
        );

        petService.save(pet);

        return "redirect:/pets";
    }

    // Mostrar formulario de edición
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        Pet pet = petService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Pet not found"));

        PetForm form = new PetForm();
        form.setName(pet.getName());
        form.setSpecies(pet.getSpecies());
        form.setAge(pet.getAge());
        form.setDescription(pet.getDescription());

        model.addAttribute("petForm", form);
        model.addAttribute("petId", id);

        return "pets/form";
    }

    // Actualizar mascota
    @PostMapping("/{id}")
    public String update(
            @PathVariable("id") long id,
            @Valid @ModelAttribute("petForm") PetForm form,
            BindingResult result,
            Model model) {

        Pet existingPet = petService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Pet not found"));

        if (result.hasErrors()) {
            model.addAttribute("petId", id);
            return "pets/form";
        }

        existingPet.setName(form.getName());
        existingPet.setSpecies(form.getSpecies());
        existingPet.setAge(form.getAge());
        existingPet.setDescription(form.getDescription());

        petService.save(existingPet);

        return "redirect:/pets";
    }

    // Eliminar mascota
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") long id) {
        petService.deleteById(id);
        return "redirect:/pets";
    }
}
