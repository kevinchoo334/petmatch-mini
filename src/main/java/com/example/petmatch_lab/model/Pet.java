package com.example.petmatch_lab.model;

public class Pet {

    private Long id;
    private String name;
    private String species;
    private int age;
    private String description;

    public Pet(Long id, String name, String species, int age, String description) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.age = age;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public int getAge() {
        return age;
    }

    public String getDescription() {
        return description;
    }
}