package com.themyntt.challenges.picpay.domain.models;

import com.themyntt.challenges.picpay.domain.core.StandardResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TransferUserResponseModel extends StandardResponse {
    private boolean isNotified;

    public TransferUserResponseModel() {}

    public TransferUserResponseModel(String message, int statusCode, boolean isNotified) {
        this.setMessage(message);
        this.setStatusCode(statusCode);
        this.isNotified = isNotified;
    }
}
