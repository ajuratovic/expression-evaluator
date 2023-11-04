package org.ajuratovic.expression_evaluator.service.persistance;

import org.ajuratovic.expression_evaluator.service.model.ExpressionModel;

import java.util.UUID;

public interface PersistenceAdapter {

    UUID saveExpression(ExpressionModel expressionModel);
    ExpressionModel getExpression(UUID id);
}
