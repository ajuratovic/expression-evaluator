package org.ajuratovic.expression_evaluator.controller.mapper;

import org.ajuratovic.expression_evaluator.controller.model.ExpressionRequest;
import org.ajuratovic.expression_evaluator.service.model.ExpressionModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExpressionRequestMapper {

    ExpressionModel requestToModel(ExpressionRequest expressionRequest);
    ExpressionRequest modelToRequest(ExpressionModel expressionModel);
}
