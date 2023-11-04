package org.ajuratovic.expression_evaluator.controller.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.UUID;

@AllArgsConstructor
@Getter
public class EvaluationRequest {

    @NotBlank
    @UUID
    private String id;

    @NotBlank
    @Size(max = 255)
    private String data;
}
