package org.ajuratovic.expression_evaluator.repository;

import org.ajuratovic.expression_evaluator.repository.entity.ExpressionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExpressionRepository extends JpaRepository<ExpressionEntity, UUID> {
}
