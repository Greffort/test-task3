package com.haulmont.testtask.backend.DAO.repository;

import com.haulmont.testtask.backend.DAO.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
 * interface defines methods for providing access to database data
 * @version 12.11.2020
 * Created by Aleksandr Kravchina
 */

@SuppressWarnings("serial")
public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {
    Optional<DoctorEntity> findByName(String name);

    void deleteById(Long id);

    Optional<DoctorEntity> findById(Long id);
}
