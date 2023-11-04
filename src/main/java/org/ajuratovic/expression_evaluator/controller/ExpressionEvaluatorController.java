package org.ajuratovic.expression_evaluator.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.ajuratovic.expression_evaluator.controller.mapper.EvaluationRequestMapper;
import org.ajuratovic.expression_evaluator.controller.mapper.ExpressionRequestMapper;
import org.ajuratovic.expression_evaluator.controller.model.EvaluationRequest;
import org.ajuratovic.expression_evaluator.controller.model.EvaluationResponse;
import org.ajuratovic.expression_evaluator.controller.model.ExpressionRequest;
import org.ajuratovic.expression_evaluator.controller.model.ExpressionResponse;
import org.ajuratovic.expression_evaluator.service.ExpressionEvaluatorService;
import org.ajuratovic.expression_evaluator.service.model.EvaluationModel;
import org.ajuratovic.expression_evaluator.service.model.ExpressionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@AllArgsConstructor
@RestController
public class ExpressionEvaluatorController {

    private ExpressionEvaluatorService expressionEvaluatorService;
    private ExpressionRequestMapper expressionRequestMapper;
    private EvaluationRequestMapper evaluationRequestMapper;

    @PostMapping(value = "/expression", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExpressionResponse> saveExpression(@RequestBody @Valid ExpressionRequest expressionRequest) {
        ExpressionModel expressionModel = expressionRequestMapper.requestToModel(expressionRequest);
        UUID savedExpressionId = expressionEvaluatorService.saveExpression(expressionModel);

        ExpressionResponse expressionResponse = new ExpressionResponse(savedExpressionId.toString());
        return ResponseEntity.ok(expressionResponse);
    }

    @PutMapping(value = "/evaluate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EvaluationResponse> evaluateDataWithExpression(@RequestBody @Valid EvaluationRequest evaluationRequest) {
        EvaluationModel evaluationModel = evaluationRequestMapper.requestToModel(evaluationRequest);
        boolean evaluationResult = expressionEvaluatorService.evaluateExpression(evaluationModel);

        EvaluationResponse evaluationResponse = new EvaluationResponse(evaluationResult);
        return ResponseEntity.ok(evaluationResponse);
    }
}
