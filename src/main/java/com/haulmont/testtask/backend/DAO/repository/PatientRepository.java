package com.haulmont.testtask.backend.DAO.repository;

import com.haulmont.testtask.backend.DAO.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
 * interface defines methods for providing access to database data
 * @version 12.11.2020
 * Created by Greffort
 */


@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
    Optional<PatientEntity> findByName(String name);

    void deleteById(Long id);

    Optional<PatientEntity> findById(Long id);
}
