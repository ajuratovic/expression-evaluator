package org.ajuratovic.expression_evaluator.repository;

import jakarta.persistence.EntityNotFoundException;
import org.ajuratovic.expression_evaluator.service.model.ExpressionModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureDataJpa
class ExpressionPersistanceTest {

    @Autowired
    PersistenceAdapterImpl persistenceAdapter;

    @Test
    void saveExpressionTest_validData() {
        ExpressionModel expressionModel = new ExpressionModel(null, "Test", "test==test");

        UUID id = persistenceAdapter.saveExpression(expressionModel);

        Assertions.assertNotNull(id);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void saveExpressionTest_invalidName(String name) {
        ExpressionModel expressionModel = new ExpressionModel(null, name, "test==test");

        TransactionSystemException transactionSystemException = Assertions.assertThrows(TransactionSystemException.class, () -> {
            persistenceAdapter.saveExpression(expressionModel);
        });

        Assertions.assertTrue(transactionSystemException.getRootCause().toString().contains("propertyPath=name"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void saveExpressionTest_invalidExpression(String expression) {
        ExpressionModel expressionModel = new ExpressionModel(null, "Test", expression);

        TransactionSystemException transactionSystemException = Assertions.assertThrows(TransactionSystemException.class, () -> {
            persistenceAdapter.saveExpression(expressionModel);
        });

        Assertions.assertTrue(transactionSystemException.getRootCause().toString().contains("propertyPath=expression"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void saveExpressionTest_invalidBothParams(String value) {
        ExpressionModel expressionModel = new ExpressionModel(null, value, value);

        TransactionSystemException transactionSystemException = Assertions.assertThrows(TransactionSystemException.class, () -> {
            persistenceAdapter.saveExpression(expressionModel);
        });

        Assertions.assertTrue(transactionSystemException.getRootCause().toString().contains("propertyPath=expression"));
        Assertions.assertTrue(transactionSystemException.getRootCause().toString().contains("propertyPath=name"));
    }

    @Test
    @Transactional
    void getExpressionTest_validData() {
        ExpressionModel expressionModel = new ExpressionModel(null, "Test", "test==test");
        UUID id = persistenceAdapter.saveExpression(expressionModel);

        ExpressionModel fetchedExpressionModel = persistenceAdapter.getExpression(id);

        Assertions.assertNotNull(fetchedExpressionModel);
        Assertions.assertEquals(id, fetchedExpressionModel.getId());
        Assertions.assertEquals("Test", fetchedExpressionModel.getName());
        Assertions.assertEquals("test==test", fetchedExpressionModel.getExpression());
    }

    @Test
    @Transactional
    void getExpressionTest_nonExistingId() {
        UUID id = UUID.randomUUID();

        JpaObjectRetrievalFailureException jpaObjectRetrievalFailureException = Assertions.assertThrows(JpaObjectRetrievalFailureException.class,
                () -> persistenceAdapter.getExpression(id));

        Assertions.assertTrue(jpaObjectRetrievalFailureException.getMessage().contains("Unable to find"));
        Assertions.assertTrue(jpaObjectRetrievalFailureException.getMessage().contains(id.toString()));
        Assertions.assertTrue(jpaObjectRetrievalFailureException.getRootCause() instanceof EntityNotFoundException);
    }

    @Test
    @Transactional
    void getExpressionTest_nullId() {

        InvalidDataAccessApiUsageException invalidDataAccessApiUsageException = Assertions.assertThrows(InvalidDataAccessApiUsageException.class,
                () -> persistenceAdapter.getExpression(null));

        Assertions.assertTrue(invalidDataAccessApiUsageException.getMessage().contains("The given id must not be null"));
        Assertions.assertTrue(invalidDataAccessApiUsageException.getRootCause() instanceof IllegalArgumentException);
    }
}