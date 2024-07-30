package com.themyntt.challenges.picpay.domain.models;

import com.themyntt.challenges.picpay.domain.core.StandardResponse;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserResponseModel extends StandardResponse {
    UserOmitStrictInfoModel user;

    public LoginUserResponseModel(String message, int statusCode) {
        this.setMessage(message);
        this.setStatusCode(statusCode);
    }

    public LoginUserResponseModel(String message, int statusCode, UserOmitStrictInfoModel user) {
        this.setMessage(message);
        this.setStatusCode(statusCode);
        this.user = user;
    }
}
