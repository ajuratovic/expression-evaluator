package org.ajuratovic.expression_evaluator.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "EXPRESSION")
@NoArgsConstructor
@Getter
@Setter
@Valid
public class ExpressionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @Column(nullable = false)
    @NotBlank
    @Size(max = 255)
    private String name;

    @Column(nullable = false)
    @NotBlank
    @Size(max = 255)
//    @LogicalExpression
    private String expression;
}
