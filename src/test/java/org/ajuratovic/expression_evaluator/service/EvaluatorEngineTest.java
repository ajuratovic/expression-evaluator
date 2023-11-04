package org.ajuratovic.expression_evaluator.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EvaluatorEngineTest {

    EvaluatorEngine evaluatorEngine = new EvaluatorEngine();

    @Test
    void evaluateDataWithExpression_simpleExpressionWithoutData() {
        String expression = "true == (false || true)";

        boolean evaluationResult = evaluatorEngine.evaluateDataWithExpression(null, expression);

        Assertions.assertTrue(evaluationResult);
    }
}