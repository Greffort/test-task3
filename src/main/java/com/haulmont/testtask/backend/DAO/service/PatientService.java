package com.haulmont.testtask.backend.DAO.service;


import com.haulmont.testtask.backend.model.Patient;

import java.util.List;

public interface PatientService {
    void addPatient(Patient patient);

    void deleteById(Long id);

    void delete(Patient patient);

    Patient getByName(String name);

    void editPatient(Patient patient);

    Patient findById(Long id);

    List<Patient> findAll();
}
