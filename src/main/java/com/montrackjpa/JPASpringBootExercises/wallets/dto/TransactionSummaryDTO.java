package com.montrackjpa.JPASpringBootExercises.wallets.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;


@Data
public class TransactionSummaryDTO {
    private long id;

    private int pocketCount;

    private int goalCount;

    private int income;

    private int expense;
}
