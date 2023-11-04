package org.ajuratovic.expression_evaluator.repository.mapper;

import org.ajuratovic.expression_evaluator.repository.entity.ExpressionEntity;
import org.ajuratovic.expression_evaluator.service.model.ExpressionModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExpressionEntityMapper {

    ExpressionModel entityToModel(ExpressionEntity expressionEntity);
    ExpressionEntity modelToEntity(ExpressionModel expressionModel);
}
