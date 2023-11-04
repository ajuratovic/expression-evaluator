package org.ajuratovic.expression_evaluator.repository;

import lombok.AllArgsConstructor;
import org.ajuratovic.expression_evaluator.repository.entity.ExpressionEntity;
import org.ajuratovic.expression_evaluator.repository.mapper.ExpressionEntityMapper;
import org.ajuratovic.expression_evaluator.service.model.ExpressionModel;
import org.ajuratovic.expression_evaluator.service.persistance.PersistenceAdapter;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@AllArgsConstructor
public class PersistenceAdapterImpl implements PersistenceAdapter {

    private ExpressionRepository expressionRepository;
    private ExpressionEntityMapper expressionEntityMapper;

    @Override
    public UUID saveExpression(ExpressionModel expressionModel) {
        ExpressionEntity expressionEntity = expressionEntityMapper.modelToEntity(expressionModel);
        ExpressionEntity savedExpressionEntity = expressionRepository.save(expressionEntity);
        UUID id = savedExpressionEntity.getId();

        return id;
    }

    @Override
    public ExpressionModel getExpression(UUID id) {
        ExpressionEntity expressionEntity = expressionRepository.getReferenceById(id);
        ExpressionModel expressionModel = expressionEntityMapper.entityToModel(expressionEntity);

        return expressionModel;
    }
}
