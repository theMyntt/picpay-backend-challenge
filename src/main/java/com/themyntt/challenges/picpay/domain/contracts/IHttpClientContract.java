package com.themyntt.challenges.picpay.domain.contracts;

public interface IHttpClientContract<Output> {
    Output send();
}
