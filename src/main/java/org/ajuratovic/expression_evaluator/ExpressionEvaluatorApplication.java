package org.ajuratovic.expression_evaluator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.ajuratovic.expression_evaluator"})
public class ExpressionEvaluatorApplication {

    public static void main(String... args) {
        SpringApplication.run(ExpressionEvaluatorApplication.class, args);
    }
}
