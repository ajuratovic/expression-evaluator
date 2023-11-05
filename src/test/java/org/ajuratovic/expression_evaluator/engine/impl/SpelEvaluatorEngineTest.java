package org.ajuratovic.expression_evaluator.engine.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ajuratovic.expression_evaluator.engine.EvaluatorEngine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.expression.spel.SpelEvaluationException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertThrows;

class SpelEvaluatorEngineTest {

    static EvaluatorEngine EVALUATOR_ENGINE;
    static String EVALUATION_DATA;

    @BeforeAll
    static void setUp() throws IOException {
        EVALUATOR_ENGINE = new SpelEvaluatorEngine();
        EVALUATION_DATA = loadEvaluationData();
    }

    @Test
    void evaluateDataWithExpression_simpleInvalidExpressionWithoutData() {
        String expression = "truThat == (false || true)";

        assertThrows(SpelEvaluationException.class, () -> EVALUATOR_ENGINE.evaluateExpression(expression));
    }

    @Test
    void evaluateDataWithExpression_simpleExpressionWithoutData() {
        String expression = "true == (false || true)";

        boolean evaluationResult = EVALUATOR_ENGINE.evaluateExpression(expression);

        Assertions.assertTrue(evaluationResult);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/expressions.csv", numLinesToSkip = 1)
    void evaluateDataWithExpression_multipleComplicatedExpressionWithData(String expression, boolean expected) {

        boolean evaluationResult = EVALUATOR_ENGINE.evaluateExpressionWithData(expression, EVALUATION_DATA);

        Assertions.assertEquals(expected, evaluationResult);
    }

    static String loadEvaluationData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        File evaluationDataFile = new ClassPathResource("evaluationData.json").getFile();
        byte[] byteArray = Files.readAllBytes(evaluationDataFile.toPath());
        String evaluationDataContent = new String(byteArray);
        String evaluationData = objectMapper.readTree(evaluationDataContent).toString();

        return evaluationData;
    }
}
