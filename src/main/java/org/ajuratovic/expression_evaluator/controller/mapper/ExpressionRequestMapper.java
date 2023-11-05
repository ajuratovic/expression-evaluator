package org.ajuratovic.expression_evaluator.controller.mapper;

import org.ajuratovic.expression_evaluator.controller.model.ExpressionRequest;
import org.ajuratovic.expression_evaluator.service.model.ExpressionModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExpressionRequestMapper {

    @Mapping(target = "id", ignore = true)
    ExpressionModel requestToModel(ExpressionRequest expressionRequest);
}
