package com.themyntt.challenges.picpay.domain.contracts;

public interface IControllerContract<Input, Output> {
    Output perform(Input dto);
}
