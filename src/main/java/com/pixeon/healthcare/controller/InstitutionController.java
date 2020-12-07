package com.pixeon.healthcare.controller;

import com.pixeon.healthcare.exception.BusinessException;
import com.pixeon.healthcare.model.Exam;
import com.pixeon.healthcare.model.Institution;
import com.pixeon.healthcare.service.ExamService;
import com.pixeon.healthcare.service.InstitutionService;
import com.pixeon.healthcare.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/institutions")
public class InstitutionController {
    private final InstitutionService institutionService;

    private final ExamService examService;

    public InstitutionController(InstitutionService institutionService, ExamService examService) {
        this.institutionService = institutionService;
        this.examService = examService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid Institution institution) {
        Institution institutionByCnpj = institutionService.findByCnpj(institution.getCnpj());

        if (institutionByCnpj != null) {
            return ResponseEntity.badRequest().body(Constants.INSTITUTION_ALREADY_REGISTERED);
        }

        institutionService.save(institution);

        return ResponseEntity.status(HttpStatus.CREATED).body(institution);
    }

    @GetMapping("{institutionId}/exams/{examId}")
    public ResponseEntity findByIstitutionIdAndExamId(@PathVariable("institutionId") Integer institutionId, @PathVariable("examId") Integer examId) throws BusinessException {
        Institution institution = institutionService.findById(institutionId);

        if (institution != null) {
            Exam exam = examService.findByIstitutionIdAndExamId(institutionId, examId);

            if (exam != null) {
                if (exam.isAccessed()) {
                    return ResponseEntity.ok(exam);
                } else if (institutionService.validateBudget(institution)) {
                    institutionService.chargeExam(institution);

                    exam.setAccessed(Boolean.TRUE);

                    examService.save(exam);
                    institutionService.save(institution);

                    return ResponseEntity.ok(exam);
                } else {
                    return ResponseEntity.badRequest().body(Constants.EXAM_INSTITUTION_WITHOUT_COIN);
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        return ResponseEntity.notFound().build();
    }
}