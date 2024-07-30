package com.themyntt.challenges.picpay.domain.contracts;

public interface IUsecaseContract<Input, Output> {
    Output run(Input dto);
}
