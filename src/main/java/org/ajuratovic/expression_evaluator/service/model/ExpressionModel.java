package org.ajuratovic.expression_evaluator.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class ExpressionModel {

    private UUID id;
    private String name;
    private String expression;

}
