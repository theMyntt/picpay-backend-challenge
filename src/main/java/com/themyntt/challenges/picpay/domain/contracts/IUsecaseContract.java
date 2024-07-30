package com.themyntt.challenges.picpay.domain.contracts;

import org.springframework.http.ResponseEntity;

public interface IUsecaseContract<Input, Output> {
    ResponseEntity<Output> run(Input dto);
}
