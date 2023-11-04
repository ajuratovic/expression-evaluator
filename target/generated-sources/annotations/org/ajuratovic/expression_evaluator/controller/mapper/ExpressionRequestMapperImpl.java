package org.ajuratovic.expression_evaluator.controller.mapper;

import java.util.UUID;
import javax.annotation.processing.Generated;
import org.ajuratovic.expression_evaluator.controller.model.ExpressionRequest;
import org.ajuratovic.expression_evaluator.service.model.ExpressionModel;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-04T22:09:27+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 19.0.1 (Homebrew)"
)
@Component
public class ExpressionRequestMapperImpl implements ExpressionRequestMapper {

    @Override
    public ExpressionModel requestToModel(ExpressionRequest expressionRequest) {
        if ( expressionRequest == null ) {
            return null;
        }

        String name = null;
        String expression = null;

        name = expressionRequest.getName();
        expression = expressionRequest.getExpression();

        UUID id = null;

        ExpressionModel expressionModel = new ExpressionModel( id, name, expression );

        return expressionModel;
    }

    @Override
    public ExpressionRequest modelToRequest(ExpressionModel expressionModel) {
        if ( expressionModel == null ) {
            return null;
        }

        String name = null;
        String expression = null;

        name = expressionModel.getName();
        expression = expressionModel.getExpression();

        ExpressionRequest expressionRequest = new ExpressionRequest( name, expression );

        return expressionRequest;
    }
}
