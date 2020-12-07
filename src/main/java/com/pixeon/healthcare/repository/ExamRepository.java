package com.pixeon.healthcare.repository;

import com.pixeon.healthcare.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExamRepository extends JpaRepository<Exam, Integer> {
    @Query("select exam from Exam exam where exam.institution.id = ?1 and exam.id = ?2")
    Exam findByIstitutionIdAndExamId(Integer institutionId, Integer examId);
}