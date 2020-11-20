package com.haulmont.testtask.backend.DAO.service.impl;

import com.haulmont.testtask.backend.DAO.entity.DoctorEntity;
import com.haulmont.testtask.backend.DAO.repository.DoctorRepository;
import com.haulmont.testtask.backend.DAO.service.DoctorService;
import com.haulmont.testtask.backend.model.Doctor;
import com.haulmont.testtask.shared.LoggerHelper;
import com.haulmont.testtask.shared.Mapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.haulmont.testtask.shared.LogMessages.DoctorServiceImpl.*;
import static com.haulmont.testtask.shared.LogMessages.ERROR_PARSING_OBJECT;

/*
 * The class implements the DoctorService interface
 * Provides end data for the user interface.
 * @version 12.11.2020
 * Created by Greffort
 */

@Service
@SuppressWarnings("serial")
public class DoctorServiceImplementation implements DoctorService {

    private static final Logger logFile = Logger.getLogger("APP2");

    private final DoctorRepository doctorRepository;

    private final PatientServiceImplementation patientServiceImplementation;

    @Autowired
    public DoctorServiceImplementation(DoctorRepository doctorRepository, PatientServiceImplementation patientServiceImplementation) {
        this.doctorRepository = doctorRepository;
        this.patientServiceImplementation = patientServiceImplementation;
    }

    @Override
    public void addDoctor(Doctor doctor) {
        try {
            doctorRepository.saveAndFlush(Mapper.toJavaObject(doctor, DoctorEntity.class));
            LoggerHelper.info(DOCTOR_WAS_ADDED, logFile);
        } catch (IOException e) {
            LoggerHelper.error(ERROR_PARSING_OBJECT + ERROR_DOCTOR_WAS_NOT_ADDED, e, logFile);
        }
    }

    @Override
    public void deleteById(Long id) {
        doctorRepository.deleteById(id);
        doctorRepository.flush();
        LoggerHelper.info(DOCTOR_WAS_DELETED, logFile);
    }

    @Override
    public void delete(Doctor doctor) {
        try {
            doctorRepository.delete(Mapper.toJavaObject(doctor, DoctorEntity.class));
            doctorRepository.flush();
            LoggerHelper.info(DOCTOR_WAS_DELETED, logFile);
        } catch (IOException e) {
            LoggerHelper.error(ERROR_PARSING_OBJECT + ERROR_DOCTOR_WAS_NOT_DELETED, e, logFile);
        }
    }

    @Override
    public Doctor getByName(String name) {
        try {
            Optional<DoctorEntity> doctorEntity = doctorRepository.findByName(name);
            LoggerHelper.info(SEARCH_FOR_DOCTOR_BY_NAME + name, logFile);
            return doctorEntity.isPresent() ? Mapper.toJavaObject(doctorEntity.get(), Doctor.class) : null;
        } catch (IOException e) {
            LoggerHelper.error(ERROR_PARSING_OBJECT + ERROR_SEARCH_FOR_DOCTOR_BY_NAME, e, logFile);
            return null;
        }
    }

    @Override
    public void editDoctor(Doctor doctor) {
        try {
            DoctorEntity doctorEntity = Mapper.toJavaObject(doctor, DoctorEntity.class);
            doctorRepository.saveAndFlush(doctorEntity);
            LoggerHelper.info(DOCTOR_WAS_EDITED, logFile);
        } catch (IOException e) {
            LoggerHelper.error(ERROR_PARSING_OBJECT + ERROR_DOCTOR_WAS_NOT_EDITED, e, logFile);
        }
    }

    @Override
    public Doctor findById(Long id) {
        try {
            Optional<DoctorEntity> doctorEntity = doctorRepository.findById(id);
            LoggerHelper.info(SEARCH_FOR_DOCTOR_BY_ID + id, logFile);
            return doctorEntity.isPresent() ? Mapper.toJavaObject(doctorEntity.get(), Doctor.class) : null;
        } catch (IOException e) {
            LoggerHelper.error(ERROR_PARSING_OBJECT + ERROR_SEARCH_FOR_DOCTOR_BY_ID, e, logFile);
            return null;
        }
    }

    @Override
    public List<Doctor> findAll() {
        List<DoctorEntity> recipe = doctorRepository.findAll();
        LoggerHelper.info(ALL_DOCTORS_FOUND, logFile);
        List<Doctor> doctorList = new ArrayList<>();

        for (DoctorEntity doctorEntity : recipe) {
            try {
                doctorList.add(Mapper.toJavaObject(doctorEntity, Doctor.class));
            } catch (IOException e) {
                LoggerHelper.error(ERROR_PARSING_OBJECT + ERROR_ALL_DOCTORS_FOUND, e, logFile);
                return null;
            }
        }
        return doctorList;
    }

}
