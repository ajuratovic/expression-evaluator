package org.ajuratovic.expression_evaluator.engine.impl;

import org.ajuratovic.expression_evaluator.engine.EvaluatorEngine;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.integration.json.JsonPropertyAccessor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpelEvaluatorEngine implements EvaluatorEngine {

    @Override
    public boolean evaluateExpression(String expression) {
        return evaluateExpressionWithData(expression, null);
    }

    @Override
    public boolean evaluateExpressionWithData(String expression, String data) {
        ExpressionParser expressionParser = new SpelExpressionParser();
        Expression parsedExpression = expressionParser.parseExpression(expression);

        StandardEvaluationContext evaluationContext = new StandardEvaluationContext(data);
        evaluationContext.setPropertyAccessors(List.of(new JsonPropertyAccessor()));
        boolean result = parsedExpression.getValue(evaluationContext, Boolean.class);

        return result;
    }
}
