package org.ajuratovic.expression_evaluator.controller.mapper;

import org.ajuratovic.expression_evaluator.controller.model.EvaluationRequest;
import org.ajuratovic.expression_evaluator.service.model.EvaluationModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EvaluationRequestMapper {

    EvaluationModel requestToModel(EvaluationRequest evaluationRequest);
    EvaluationRequest modelToRequest(EvaluationModel expression);
}
