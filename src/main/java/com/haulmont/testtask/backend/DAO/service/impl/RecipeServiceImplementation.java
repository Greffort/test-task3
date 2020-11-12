package com.haulmont.testtask.backend.DAO.service.impl;

import com.haulmont.testtask.backend.DAO.entity.DoctorEntity;
import com.haulmont.testtask.backend.DAO.entity.PatientEntity;
import com.haulmont.testtask.backend.DAO.entity.RecipeEntity;
import com.haulmont.testtask.backend.DAO.repository.RecipeRepository;
import com.haulmont.testtask.backend.DAO.service.RecipeService;
import com.haulmont.testtask.backend.model.Doctor;
import com.haulmont.testtask.backend.model.Patient;
import com.haulmont.testtask.backend.model.Recipe;
import com.haulmont.testtask.backend.model.enumerators.Priority;
import com.haulmont.testtask.shared.LoggerHelper;
import com.haulmont.testtask.shared.Mapper;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.haulmont.testtask.shared.LogMessages.ERROR_PARSING_OBJECT;
import static com.haulmont.testtask.shared.LogMessages.RecipeServiceImpl.*;

@Service
public class RecipeServiceImplementation implements RecipeService {

    private static final Logger logFile = Logger.getLogger("APP2");

    private final RecipeRepository recipeRepository;

    public RecipeServiceImplementation(RecipeRepository RecipeRepository) {
        this.recipeRepository = RecipeRepository;
    }

    @Override
    public void addRecipe(@NotNull Recipe recipe) {
        try {
            if (recipe.getPriority() == null) {
                recipe.setPriority(Priority.NORMAL);
            }
            recipeRepository.saveAndFlush(Mapper.toJavaObject(recipe, RecipeEntity.class));
            LoggerHelper.info(RECIPE_WAS_ADDED, logFile);
        } catch (IOException e) {
            LoggerHelper.error(ERROR_PARSING_OBJECT + ERROR_RECIPE_WAS_NOT_ADDED, e, logFile);
        }
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
        recipeRepository.flush();
        LoggerHelper.info(RECIPE_WAS_DELETED, logFile);
    }

    @Override
    public void editRecipe(Recipe recipe) {
        try {
            RecipeEntity recipeEntity = Mapper.toJavaObject(recipe, RecipeEntity.class);
            recipeRepository.saveAndFlush(recipeEntity);
            LoggerHelper.info(RECIPE_WAS_EDITED, logFile);
        } catch (IOException e) {
            LoggerHelper.error(ERROR_PARSING_OBJECT + ERROR_RECIPE_WAS_NOT_EDITED, e, logFile);
        }
    }

    @Override
    public Recipe findById(Long id) {
        try {
            Optional<RecipeEntity> recipeEntity = recipeRepository.findById(id);
            LoggerHelper.info(SEARCH_FOR_RECIPE_BY_ID, logFile);
            return recipeEntity.isPresent() ? Mapper.toJavaObject(recipeEntity.get(), Recipe.class) : null;
        } catch (IOException e) {
            LoggerHelper.error(ERROR_PARSING_OBJECT + ERROR_SEARCH_FOR_RECIPE_BY_ID, e, logFile);
            return null;
        }
    }

    @Override
    public boolean findRecipeByPatientOrderById(Patient patient) {
        try {
            LoggerHelper.info(FOUND_RECIPE_BY_PATIENT_ID, logFile);
            return recipeRepository.findRecipeEntitiesByPatientOrderById(Mapper.toJavaObject(patient, PatientEntity.class)).size() > 0;
        } catch (IOException e) {
            LoggerHelper.error(ERROR_PARSING_OBJECT + ERROR_FOUND_RECIPE_BY_PATIENT_ID, e, logFile);
            return false;
        }
    }

    @Override
    public boolean findRecipeByDoctorOrderById(Doctor doctor) {
        try {
            LoggerHelper.info(FOUND_RECIPE_BY_DOCTOR_ID, logFile);
            return recipeRepository.findRecipeEntitiesByDoctorOrderById(Mapper.toJavaObject(doctor, DoctorEntity.class)).size() > 0;
        } catch (IOException e) {
            LoggerHelper.error(ERROR_PARSING_OBJECT + ERROR_FOUND_RECIPE_BY_DOCTOR_ID, e, logFile);
            return false;
        }
    }

    @Override
    public List<Recipe> findRecipeByDoctorId(Doctor doctor) {
        try {
            List<RecipeEntity> recipes = recipeRepository.findRecipeEntitiesByDoctorOrderById(Mapper.toJavaObject(doctor, DoctorEntity.class));
            LoggerHelper.info(FOUND_RECIPE_BY_DOCTOR_ID, logFile);
            List<Recipe> recipeList = new ArrayList<>();

            for (RecipeEntity recipeEntity : recipes) {
                recipeList.add(Mapper.toJavaObject(recipeEntity, Recipe.class));
            }
            return recipeList;
        } catch (IOException e) {
            LoggerHelper.error(ERROR_PARSING_OBJECT + ERROR_FOUND_RECIPE_BY_DOCTOR_ID, e, logFile);
            return null;
        }
    }

    @Override
    public List<Recipe> findAll() {
        List<RecipeEntity> recipes = recipeRepository.findAll();
        LoggerHelper.info(ALL_RECIPES_FOUND, logFile);
        List<Recipe> recipeList = new ArrayList<>();

        for (RecipeEntity recipeEntity : recipes) {
            try {
                recipeList.add(Mapper.toJavaObject(recipeEntity, Recipe.class));
            } catch (IOException e) {
                LoggerHelper.error(ERROR_PARSING_OBJECT + ERROR_ALL_RECIPES_FOUND, e, logFile);
            }
        }
        return recipeList;
    }
}
