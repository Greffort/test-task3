package com.haulmont.testtask.backend.DAO.service;

import com.haulmont.testtask.backend.model.Doctor;
import com.haulmont.testtask.backend.model.Patient;
import com.haulmont.testtask.backend.model.Recipe;

import java.util.List;

/*
 * Interface RecipeService
 * @version 12.11.2020
 * Created by Aleksandr Kravchina
 */

public interface RecipeService {
    void addRecipe(Recipe recipeEntity);

    void deleteById(Long id);

    void editRecipe(Recipe recipeEntity);

    Recipe findById(Long id);

    boolean findRecipeByPatientOrderById(Patient patient);

    boolean findRecipeByDoctorOrderById(Doctor doctor);

    List<Recipe> findRecipeByDoctorId(Doctor doctor);

    List<Recipe> findAll();
}
