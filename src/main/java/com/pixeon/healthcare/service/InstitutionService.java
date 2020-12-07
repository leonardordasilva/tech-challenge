package com.pixeon.healthcare.service;

import com.pixeon.healthcare.exception.BusinessException;
import com.pixeon.healthcare.model.Institution;
import com.pixeon.healthcare.repository.InstitutionRepository;
import com.pixeon.healthcare.utils.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InstitutionService {
    private final InstitutionRepository institutionRepository;

    @Value("${default.exam.cost}")
    private Integer examCost;

    public InstitutionService(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }

    public Institution findById(Integer id) throws BusinessException {
        if (id == null) {
            throw new BusinessException(Constants.INSTITUTION, Constants.INSTITUTION_ID_REQUIRED);
        }

        Optional<Institution> institutionOptional = institutionRepository.findById(id);

        return institutionOptional.orElse(null);

    }

    public Institution findByCnpj(String cnpj) {
        return institutionRepository.findByCnpj(cnpj);
    }

    public void save(Institution institution) {
        institutionRepository.save(institution);
    }

    public Boolean validateBudget(Institution institution) {
        return institution.getAmountCoin() > 0;
    }

    public void chargeExam(Institution institution) {
        institution.setAmountCoin(institution.getAmountCoin() - examCost);

        institutionRepository.save(institution);
    }
}