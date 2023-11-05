package org.ajuratovic.expression_evaluator.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.ajuratovic.expression_evaluator.controller.mapper.EvaluationRequestMapper;
import org.ajuratovic.expression_evaluator.controller.mapper.ExpressionRequestMapper;
import org.ajuratovic.expression_evaluator.controller.model.EvaluationResponse;
import org.ajuratovic.expression_evaluator.controller.model.ExpressionRequest;
import org.ajuratovic.expression_evaluator.controller.model.ExpressionResponse;
import org.ajuratovic.expression_evaluator.service.ExpressionEvaluatorService;
import org.ajuratovic.expression_evaluator.service.model.EvaluationModel;
import org.ajuratovic.expression_evaluator.service.model.ExpressionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@RestController
public class ExpressionEvaluatorController {

    private final Logger logger = LoggerFactory.getLogger(ExpressionEvaluatorController.class);

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

    @PutMapping(value = "/evaluate/{expressionId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EvaluationResponse> evaluateExpressionWithData(
            @PathVariable @NotBlank @org.hibernate.validator.constraints.UUID UUID expressionId,
            @RequestBody @NotBlank @Size(max = 255) String evaluationData) {
        EvaluationModel evaluationModel = evaluationRequestMapper.requestToModel(expressionId, evaluationData);
        boolean evaluationResult = expressionEvaluatorService.evaluateExpression(evaluationModel);

        EvaluationResponse evaluationResponse = new EvaluationResponse(evaluationResult);
        return ResponseEntity.ok(evaluationResponse);
    }
}
