package org.ajuratovic.expression_evaluator.engine;

public interface EvaluatorEngine {

    boolean evaluateExpression(String expression);

    boolean evaluateExpressionWithData(String expression, String data);
}
