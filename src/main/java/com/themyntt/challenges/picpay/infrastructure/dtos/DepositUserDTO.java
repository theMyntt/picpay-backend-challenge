package com.themyntt.challenges.picpay.infrastructure.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositUserDTO {
    @NotNull
    private Long id;

    @NotNull
    private double value;
}
