package com.haulmont.testtask.backend.DAO.repository;

import com.haulmont.testtask.backend.DAO.entity.DoctorEntity;
import com.haulmont.testtask.backend.DAO.entity.PatientEntity;
import com.haulmont.testtask.backend.DAO.entity.RecipeEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*
 * interface defines methods for providing access to database data
 * @version 12.11.2020
 * Created by Greffort
 */


@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {
    void deleteById(@NotNull Long id);

    @NotNull Optional<RecipeEntity> findById(@NotNull Long id);

    List<RecipeEntity> findRecipeEntitiesByPatientOrderById(PatientEntity patient);

    List<RecipeEntity> findRecipeEntitiesByDoctorOrderById(DoctorEntity doctor);
}
