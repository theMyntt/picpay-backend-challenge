package com.themyntt.challenges.picpay.infrastructure.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferUserDTO {
    @NotNull
    @Min(value = 1)
    private double value;

    @NotNull
    private Long payer;

    @NotNull
    private Long payee;
}
