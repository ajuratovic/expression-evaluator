package org.ajuratovic.expression_evaluator.controller.mapper;

import java.util.UUID;
import javax.annotation.processing.Generated;
import org.ajuratovic.expression_evaluator.controller.model.EvaluationRequest;
import org.ajuratovic.expression_evaluator.service.model.EvaluationModel;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-04T22:09:28+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 19.0.1 (Homebrew)"
)
@Component
public class EvaluationRequestMapperImpl implements EvaluationRequestMapper {

    @Override
    public EvaluationModel requestToModel(EvaluationRequest evaluationRequest) {
        if ( evaluationRequest == null ) {
            return null;
        }

        UUID id = null;
        String data = null;

        if ( evaluationRequest.getId() != null ) {
            id = UUID.fromString( evaluationRequest.getId() );
        }
        data = evaluationRequest.getData();

        EvaluationModel evaluationModel = new EvaluationModel( id, data );

        return evaluationModel;
    }

    @Override
    public EvaluationRequest modelToRequest(EvaluationModel expression) {
        if ( expression == null ) {
            return null;
        }

        String id = null;
        String data = null;

        if ( expression.getId() != null ) {
            id = expression.getId().toString();
        }
        data = expression.getData();

        EvaluationRequest evaluationRequest = new EvaluationRequest( id, data );

        return evaluationRequest;
    }
}
