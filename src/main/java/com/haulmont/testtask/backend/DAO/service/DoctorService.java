package com.haulmont.testtask.backend.DAO.service;

import com.haulmont.testtask.backend.model.Doctor;

import java.util.List;

/*
 * Interface DoctorService
 * @version 12.11.2020
 * Created by Greffort
 */

@SuppressWarnings("serial")
public interface DoctorService {
    void addDoctor(Doctor doctor);

    void deleteById(Long id);

    void delete(Doctor doctor);

    Doctor getByName(String name);

    void editDoctor(Doctor doctor);

    Doctor findById(Long id);

    List<Doctor> findAll();
}
