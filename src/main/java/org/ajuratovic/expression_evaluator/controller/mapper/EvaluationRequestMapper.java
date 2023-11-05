package org.ajuratovic.expression_evaluator.controller.mapper;

import org.ajuratovic.expression_evaluator.service.model.EvaluationModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface EvaluationRequestMapper {

    @Mapping(target = "id", source = "expressionId")
    @Mapping(target = "data", source = "evaluationData")
    EvaluationModel requestToModel(UUID expressionId, String evaluationData);
}
