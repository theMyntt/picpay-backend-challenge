package com.themyntt.challenges.picpay.domain.models;

import com.themyntt.challenges.picpay.domain.core.StandardResponse;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class DepositUserResponseModel extends StandardResponse {
    private double value;

    public DepositUserResponseModel() {}

    public DepositUserResponseModel(String message, int statusCode) {
        this.setMessage(message);
        this.setStatusCode(statusCode);
    }

    public DepositUserResponseModel(String message, int statusCode, double value) {
        this.setMessage(message);
        this.setStatusCode(statusCode);
        this.value = value;
    }
}
