package com.haulmont.testtask.backend.DAO.service.impl;

import com.haulmont.testtask.backend.DAO.entity.PatientEntity;
import com.haulmont.testtask.backend.DAO.repository.PatientRepository;
import com.haulmont.testtask.backend.DAO.service.PatientService;
import com.haulmont.testtask.backend.model.Patient;
import com.haulmont.testtask.shared.LoggerHelper;
import com.haulmont.testtask.shared.Mapper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.haulmont.testtask.shared.LogMessages.ERROR_PARSING_OBJECT;
import static com.haulmont.testtask.shared.LogMessages.PatientServiceImpl.*;

/*
 * The class implements the PatientService interface
 * Provides end data for the user interface.
 * @version 12.11.2020
 * Created by Aleksandr Kravchina
 */

@Service
public class PatientServiceImplementation implements PatientService {

    private static final Logger logFile = Logger.getLogger("APP2");

    private final PatientRepository patientRepository;

    public PatientServiceImplementation(PatientRepository PatientRepository) {
        this.patientRepository = PatientRepository;
    }

    @Override
    public void addPatient(Patient patient) {
        try {
            patientRepository.saveAndFlush(Mapper.toJavaObject(patient, PatientEntity.class));
            LoggerHelper.info(PATIENT_WAS_ADDED, logFile);
        } catch (IOException e) {
            LoggerHelper.error(ERROR_PARSING_OBJECT + ERROR_PATIENT_WAS_NOT_ADDED, e, logFile);
        }
    }

    @Override
    public void deleteById(Long id) {
        patientRepository.deleteById(id);
        patientRepository.flush();
        LoggerHelper.info(PATIENT_WAS_DELETED, logFile);
    }

    @Override
    public void delete(Patient patient) {
        try {
            patientRepository.delete(Mapper.toJavaObject(patient, PatientEntity.class));
            patientRepository.flush();
            LoggerHelper.info(PATIENT_WAS_DELETED, logFile);
        } catch (IOException e) {
            LoggerHelper.error(ERROR_PARSING_OBJECT + ERROR_PATIENT_WAS_NOT_DELETED, e, logFile);
        }
    }

    @Override
    public Patient getByName(String name) {
        try {
            Optional<PatientEntity> patientEntity = patientRepository.findByName(name);
            LoggerHelper.info(SEARCH_FOR_PATIENT_BY_NAME, logFile);
            return patientEntity.isPresent() ? Mapper.toJavaObject(patientEntity.get(), Patient.class) : null;
        } catch (IOException e) {
            LoggerHelper.error(ERROR_PARSING_OBJECT + ERROR_SEARCH_FOR_PATIENT_BY_NAME, e, logFile);
            return null;
        }
    }

    @Override
    public void editPatient(Patient patient) {
        try {
            PatientEntity patientEntity = Mapper.toJavaObject(patient, PatientEntity.class);
            patientRepository.saveAndFlush(patientEntity);
            LoggerHelper.info(PATIENT_WAS_EDITED, logFile);
        } catch (IOException e) {
            LoggerHelper.error(ERROR_PARSING_OBJECT + ERROR_PATIENT_WAS_NOT_EDITED, e, logFile);
        }
    }

    @Override
    public Patient findById(Long id) {
        try {
            Optional<PatientEntity> patientEntity = patientRepository.findById(id);
            LoggerHelper.info(SEARCH_FOR_PATIENT_BY_ID, logFile);
            return patientEntity.isPresent() ? Mapper.toJavaObject(patientEntity.get(), Patient.class) : null;
        } catch (IOException e) {
            LoggerHelper.error(ERROR_PARSING_OBJECT + ERROR_SEARCH_FOR_PATIENT_BY_ID, e, logFile);
            return null;
        }
    }

    @Override
    public List<Patient> findAll() {
        List<PatientEntity> recipe = patientRepository.findAll();
        LoggerHelper.info(ALL_PATIENTS_FOUND, logFile);
        List<Patient> patientList = new ArrayList<>();

        for (PatientEntity patientEntity : recipe) {
            try {
                patientList.add(Mapper.toJavaObject(patientEntity, Patient.class));
            } catch (IOException e) {
                LoggerHelper.error(ERROR_PARSING_OBJECT + ERROR_ALL_PATIENTS_FOUND, e, logFile);
            }
        }
        return patientList;
    }
}
