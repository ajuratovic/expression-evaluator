package org.ajuratovic.expression_evaluator;

import org.ajuratovic.expression_evaluator.controller.ExpressionEvaluatorController;
import org.ajuratovic.expression_evaluator.controller.mapper.EvaluationRequestMapper;
import org.ajuratovic.expression_evaluator.controller.mapper.ExpressionRequestMapper;
import org.ajuratovic.expression_evaluator.engine.EvaluatorEngine;
import org.ajuratovic.expression_evaluator.exception.GlobalExceptionHandler;
import org.ajuratovic.expression_evaluator.repository.ExpressionRepository;
import org.ajuratovic.expression_evaluator.repository.mapper.ExpressionEntityMapper;
import org.ajuratovic.expression_evaluator.service.ExpressionEvaluatorService;
import org.ajuratovic.expression_evaluator.service.persistance.PersistenceAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExpressionEvaluatorApplicationTest {

    @Autowired
    EvaluationRequestMapper evaluationRequestMapper;

    @Autowired
    ExpressionRequestMapper expressionRequestMapper;

    @Autowired
    ExpressionEvaluatorController expressionEvaluatorController;

    @Autowired
    GlobalExceptionHandler globalExceptionHandler;

    @Autowired
    ExpressionEntityMapper expressionEntityMapper;

    @Autowired
    ExpressionRepository expressionRepository;

    @Autowired
    PersistenceAdapter persistenceAdapter;

    @Autowired
    EvaluatorEngine evaluatorEngine;

    @Autowired
    ExpressionEvaluatorService expressionEvaluatorService;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(expressionRequestMapper);
        Assertions.assertNotNull(evaluationRequestMapper);
        Assertions.assertNotNull(expressionEvaluatorController);
        Assertions.assertNotNull(globalExceptionHandler);
        Assertions.assertNotNull(expressionEntityMapper);
        Assertions.assertNotNull(expressionRepository);
        Assertions.assertNotNull(persistenceAdapter);
        Assertions.assertNotNull(evaluatorEngine);
        Assertions.assertNotNull(expressionEvaluatorService);
    }

}