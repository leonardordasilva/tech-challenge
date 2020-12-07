package com.pixeon.healthcare.model;

import com.pixeon.healthcare.utils.Constants;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
@NoArgsConstructor
@Data
public class Institution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = Constants.INSTITUTION_NAME_IS_REQUIRED)
    private String name;

    @NotEmpty(message = Constants.INSTITUTION_CNPJ_IS_REQUIRED)
    private String cnpj;

    private Integer amountCoin = 20;

    public Institution(String id) {
        if (id.isEmpty()) {
            this.id = null;
        } else {
            this.id = Integer.valueOf(id);
        }
    }
}