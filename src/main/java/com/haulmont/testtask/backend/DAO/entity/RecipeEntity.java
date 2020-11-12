package com.haulmont.testtask.backend.DAO.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.haulmont.testtask.backend.DAO.entity.enumerators.Priority;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/*
 * Class defines a database entity Recipe
 * @version 12.11.2020
 * Created by Greffort
 */

@Data
@Entity
@Table(name = "RECIPE")
public class RecipeEntity {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault());

    private static final String DEFAULT_DESCRIPTION = "None";
    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.now();
    private static final LocalDate DEFAULT_VALIDITY = LocalDate.now().plusDays(3L);
    private static final Priority DEFAULT_PRIORITY = Priority.NORMAL;

    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "ID_PATIENT", nullable = false)
    private PatientEntity patient;

    @ManyToOne
    @JoinColumn(name = "ID_DOCTOR", nullable = false)
    private DoctorEntity doctor;

    @Column(name = "CREATEDATE")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createDate;

    @Column(name = "VALIDITY")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate validity;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    public RecipeEntity() {
    }

    public RecipeEntity(PatientEntity patient, DoctorEntity doctor) {
        this(DEFAULT_DESCRIPTION,
                patient,
                doctor,
                DEFAULT_CREATE_DATE,
                DEFAULT_VALIDITY,
                DEFAULT_PRIORITY);
    }

    public RecipeEntity(String description,
                        PatientEntity patient,
                        DoctorEntity doctor,
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

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", patientId=" + patient.getId() +
                ", doctorId=" + doctor.getId() +
                ", createDate='" + createDate + '\'' +
                ", validity='" + validity + '\'' +
                ", priority=" + priority +
                '}';
    }
}
