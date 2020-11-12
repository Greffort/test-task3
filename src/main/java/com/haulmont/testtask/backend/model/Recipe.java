package com.haulmont.testtask.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.haulmont.testtask.backend.model.enumerators.Priority;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/*
 * Defines a Recipe object.
 * @version 12.11.2020
 * Created by Aleksandr Kravchina
 */

@Data
public class Recipe implements Serializable {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.getDefault());

    private static final String DEFAULT_DESCRIPTION = "None";
    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.now();
    private static final LocalDate DEFAULT_VALIDITY = LocalDate.now().plusDays(3L);
    private static final Priority DEFAULT_PRIORITY = Priority.NORMAL;

    private Long id;
    private String description;
    private Patient patient;
    private Doctor doctor;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate validity;
    private Priority priority;

    public Recipe() {

    }

    public Recipe(Patient patient, Doctor doctor) {
        this(DEFAULT_DESCRIPTION,
                patient,
                doctor,
                DEFAULT_CREATE_DATE,
                DEFAULT_VALIDITY,
                DEFAULT_PRIORITY);
    }

    public Recipe(String description,
                  Patient patient,
                  Doctor doctor,
                  LocalDate createDate,
                  LocalDate validity,
                  Priority priority) {
        this.description = description;
        this.patient = patient;
        this.doctor = doctor;
        this.createDate = createDate;
        this.validity = validity;
        this.priority = priority;
    }
}
