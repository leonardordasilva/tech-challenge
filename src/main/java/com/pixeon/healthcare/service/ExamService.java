package com.pixeon.healthcare.service;

import com.pixeon.healthcare.model.Exam;
import com.pixeon.healthcare.repository.ExamRepository;
import org.springframework.stereotype.Service;

@Service
public class ExamService {
    private ExamRepository examRepository;

    public ExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    public void save(Exam exam) {
        examRepository.save(exam);
    }

    public Exam findByIstitutionIdAndExamId(Integer institutionId, Integer examId) {
        return examRepository.findByIstitutionIdAndExamId(institutionId, examId);
    }
}