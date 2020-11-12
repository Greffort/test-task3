package com.haulmont.testtask.backend.DAO.repository;

import com.haulmont.testtask.backend.DAO.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@SuppressWarnings("serial")
public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {
    Optional<DoctorEntity> findByName(String name);

    void deleteById(Long id);

    Optional<DoctorEntity> findById(Long id);

}

