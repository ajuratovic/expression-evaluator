package org.ajuratovic.expression_evaluator.controller;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ajuratovic.expression_evaluator.ExpressionEvaluatorApplication;
import org.ajuratovic.expression_evaluator.controller.model.EvaluationRequest;
import org.ajuratovic.expression_evaluator.controller.model.EvaluationResponse;
import org.ajuratovic.expression_evaluator.controller.model.ExpressionRequest;
import org.ajuratovic.expression_evaluator.controller.model.ExpressionResponse;
import org.ajuratovic.expression_evaluator.exception.ErrorMessage;
import org.ajuratovic.expression_evaluator.repository.ExpressionRepository;
import org.ajuratovic.expression_evaluator.repository.entity.ExpressionEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = ExpressionEvaluatorApplication.class)
@AutoConfigureMockMvc
class ExpressionEvaluatorControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ExpressionRepository expressionRepository;

    @Test
    void saveLogicalExpression_validData() throws Exception {
        ExpressionRequest expressionRequest = new ExpressionRequest("Test", "test==true");
        String expressionRequestAsJson = objectMapper.writeValueAsString(expressionRequest);

        ResultActions resultActions = mockMvc.perform(post("/expression")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expressionRequestAsJson));

        MvcResult mvcResult = resultActions.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(isValidJson(responseBody, ExpressionResponse.class));
    }

    @Test
    void saveLogicalExpression_invalidHttpMethod() throws Exception {
        ExpressionRequest expressionRequest = new ExpressionRequest("Test", "test==true");
        String expressionRequestAsJson = objectMapper.writeValueAsString(expressionRequest);

        ResultActions resultActions = mockMvc.perform(put("/expression")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expressionRequestAsJson));

        MvcResult mvcResult = resultActions.andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(isValidJson(responseBody, ErrorMessage.class));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void saveLogicalExpression_invalidName(String name) throws Exception {
        ExpressionRequest expressionRequest = new ExpressionRequest(name, "test==true");
        String expressionRequestAsJson = objectMapper.writeValueAsString(expressionRequest);

        ResultActions resultActions = mockMvc.perform(post("/expression")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expressionRequestAsJson));

        MvcResult mvcResult = resultActions.andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(isValidJson(responseBody, ErrorMessage.class));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void saveLogicalExpression_invalidExpression(String expression) throws Exception {
        ExpressionRequest expressionRequest = new ExpressionRequest("Test", expression);
        String expressionRequestAsJson = objectMapper.writeValueAsString(expressionRequest);

        ResultActions resultActions = mockMvc.perform(post("/expression")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expressionRequestAsJson));

        MvcResult mvcResult = resultActions.andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(isValidJson(responseBody, ErrorMessage.class));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void saveLogicalExpression_invalidBothParams(String value) throws Exception {
        ExpressionRequest expressionRequest = new ExpressionRequest(value, value);
        String expressionRequestAsJson = objectMapper.writeValueAsString(expressionRequest);

        ResultActions resultActions = mockMvc.perform(post("/expression")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expressionRequestAsJson));

        MvcResult mvcResult = resultActions.andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(isValidJson(responseBody, ErrorMessage.class));
    }

    @Test
    void evaluateLogicalExpression_validData() throws Exception {
        ExpressionEntity expressionEntity = new ExpressionEntity();
        expressionEntity.setExpression("true==true");
        expressionEntity.setName("Test");
        expressionEntity = expressionRepository.save(expressionEntity);

        EvaluationRequest evaluationRequest = new EvaluationRequest(expressionEntity.getId().toString(), "data");
        String evaluationRequestAsJson = objectMapper.writeValueAsString(evaluationRequest);

        ResultActions resultActions = mockMvc.perform(put("/evaluate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(evaluationRequestAsJson));

        MvcResult mvcResult = resultActions.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(isValidJson(responseBody, EvaluationResponse.class));
        EvaluationResponse evaluationResponse = objectMapper.readValue(responseBody, EvaluationResponse.class);
        Assertions.assertTrue(evaluationResponse.isResult());
    }

    @Test
    void evaluateLogicalExpression_invalidHttpMethod() throws Exception {
        ExpressionEntity expressionEntity = new ExpressionEntity();
        expressionEntity.setExpression("test==test");
        expressionEntity.setName("Test");
        expressionEntity = expressionRepository.save(expressionEntity);

        EvaluationRequest evaluationRequest = new EvaluationRequest(expressionEntity.getId().toString(), "data");
        String evaluationRequestAsJson = objectMapper.writeValueAsString(evaluationRequest);

        ResultActions resultActions = mockMvc.perform(post("/evaluate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(evaluationRequestAsJson));

        MvcResult mvcResult = resultActions.andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(isValidJson(responseBody, EvaluationResponse.class));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"test", "mock-UUID", "788ade84-6699-466-8595-974800d3a1d7", "6ea2f1fc-4b9e-4cf1-970b-2bc2f34235d0"})
    void evaluateLogicalExpression_invalidId(String id) throws Exception {
        EvaluationRequest evaluationRequest = new EvaluationRequest(id, "data");
        String evaluationRequestAsJson = objectMapper.writeValueAsString(evaluationRequest);

        ResultActions resultActions = mockMvc.perform(put("/evaluate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(evaluationRequestAsJson));

        MvcResult mvcResult = resultActions.andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(isValidJson(responseBody, ErrorMessage.class));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void evaluateLogicalExpression_invalidEvaluationData(String data) throws Exception {
        String id = UUID.randomUUID().toString();
        EvaluationRequest evaluationRequest = new EvaluationRequest(id, data);
        String evaluationRequestAsJson = objectMapper.writeValueAsString(evaluationRequest);

        ResultActions resultActions = mockMvc.perform(put("/evaluate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(evaluationRequestAsJson));

        MvcResult mvcResult = resultActions.andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(isValidJson(responseBody, ErrorMessage.class));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void evaluateLogicalExpression_invalidBothParams(String data) throws Exception {
        String id = UUID.randomUUID().toString();
        EvaluationRequest evaluationRequest = new EvaluationRequest(data, data);
        String evaluationRequestAsJson = objectMapper.writeValueAsString(evaluationRequest);

        ResultActions resultActions = mockMvc.perform(put("/evaluate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(evaluationRequestAsJson));

        MvcResult mvcResult = resultActions.andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(isValidJson(responseBody, ErrorMessage.class));
    }

    private boolean isValidJson(String json, Class clazz) {
        try {
            objectMapper.readValue(json, clazz);
        } catch (JacksonException e) {
            return false;
        }
        return true;
    }
}