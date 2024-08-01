package com.themyntt.challenges.picpay.domain.models;

import lombok.Data;

@Data
public class AuthorizeHttpResponseModel {
    private String status;
    private Data data;

    @lombok.Data
    public static class Data {
        private boolean authorization;
    }
}
