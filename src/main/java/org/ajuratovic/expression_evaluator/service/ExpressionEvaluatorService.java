package org.ajuratovic.expression_evaluator.service;


import lombok.AllArgsConstructor;
import org.ajuratovic.expression_evaluator.service.model.EvaluationModel;
import org.ajuratovic.expression_evaluator.service.model.ExpressionModel;
import org.ajuratovic.expression_evaluator.service.persistance.PersistenceAdapter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ExpressionEvaluatorService {

    private PersistenceAdapter persistenceAdapter;
    private EvaluatorEngine evaluatorEngine;

    @Transactional
    public UUID saveExpression(ExpressionModel expressionModel) {
        UUID savedExpressionId = persistenceAdapter.saveExpression(expressionModel);

        return savedExpressionId;
    }

    public boolean evaluateExpression(EvaluationModel evaluationModel) {
        UUID expressionId = evaluationModel.getId();
        ExpressionModel expressionModel = persistenceAdapter.getExpression(expressionId);

        boolean evaluationResult = evaluatorEngine.evaluateDataWithExpression(evaluationModel.getData(), expressionModel.getExpression());
        return evaluationResult;
    }
}
