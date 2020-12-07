package com.pixeon.healthcare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pixeon.healthcare.utils.Constants;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne
    private Institution institution;

    @NotEmpty(message = Constants.EXAM_PATIENT_NAME_REQUIRED)
    private String patientName;

    @NotNull(message = Constants.EXAM_PATIENT_AGE_MIN)
    private Integer patientAge;

    @NotNull(message = Constants.EXAM_PATIENT_GENDER_REQUIRED)
    @Enumerated(EnumType.STRING)
    private Gender patientGender;

    @NotEmpty(message = Constants.EXAM_PHYSICIAN_NAME_REQUIRED)
    private String physicianName;

    @NotEmpty(message = Constants.EXAM_PHYSICIAN_CRM_REQUIRED)
    private String physicianCRM;

    @NotEmpty(message = Constants.EXAM_PROCEDURE_NAME_REQUIRED)
    private String procedureName;

    @JsonIgnore
    private Boolean accessed = Boolean.FALSE;

    public Boolean isAccessed() {
        return this.accessed.equals(Boolean.TRUE);
    }
}