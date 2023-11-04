package org.ajuratovic.expression_evaluator.repository.mapper;

import java.util.UUID;
import javax.annotation.processing.Generated;
import org.ajuratovic.expression_evaluator.repository.entity.ExpressionEntity;
import org.ajuratovic.expression_evaluator.service.model.ExpressionModel;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-04T22:09:28+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 19.0.1 (Homebrew)"
)
@Component
public class ExpressionEntityMapperImpl implements ExpressionEntityMapper {

    @Override
    public ExpressionModel entityToModel(ExpressionEntity expressionEntity) {
        if ( expressionEntity == null ) {
            return null;
        }

        UUID id = null;
        String name = null;
        String expression = null;

        id = expressionEntity.getId();
        name = expressionEntity.getName();
        expression = expressionEntity.getExpression();

        ExpressionModel expressionModel = new ExpressionModel( id, name, expression );

        return expressionModel;
    }

    @Override
    public ExpressionEntity modelToEntity(ExpressionModel expressionModel) {
        if ( expressionModel == null ) {
            return null;
        }

        ExpressionEntity expressionEntity = new ExpressionEntity();

        expressionEntity.setId( expressionModel.getId() );
        expressionEntity.setName( expressionModel.getName() );
        expressionEntity.setExpression( expressionModel.getExpression() );

        return expressionEntity;
    }
}
