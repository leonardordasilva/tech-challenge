package com.pixeon.healthcare.controller;

import com.pixeon.healthcare.exception.BusinessException;
import com.pixeon.healthcare.model.Exam;
import com.pixeon.healthcare.model.Institution;
import com.pixeon.healthcare.service.ExamService;
import com.pixeon.healthcare.service.InstitutionService;
import com.pixeon.healthcare.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/exams")
public class ExamController {
    private final ExamService examService;

    private final InstitutionService institutionService;

    public ExamController(ExamService examService, InstitutionService institutionService) {
        this.examService = examService;
        this.institutionService = institutionService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid Exam exam) throws BusinessException {
        Institution institution = institutionService.findById(exam.getInstitution().getId());

        if (institution != null) {
            Boolean isBudget = institutionService.validateBudget(institution);

            if (isBudget) {
                examService.save(exam);

                institutionService.chargeExam(institution);

                return ResponseEntity.status(HttpStatus.CREATED).body(Constants.EXAM_CREATE);
            } else {
                return ResponseEntity.badRequest().body(Constants.EXAM_INSTITUTION_WITHOUT_COIN);
            }
        }

        return ResponseEntity.badRequest().body(Constants.INSTITUTION_NOT_EXISTS);
    }
}