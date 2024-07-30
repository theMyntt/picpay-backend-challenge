package com.themyntt.challenges.picpay.infrastructure.controllers;

import com.themyntt.challenges.picpay.application.usecases.NewUserUsecase;
import com.themyntt.challenges.picpay.domain.contracts.IControllerContract;
import com.themyntt.challenges.picpay.domain.core.StandardResponse;
import com.themyntt.challenges.picpay.infrastructure.dtos.NewUserDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/v1/")
public class NewUserController implements IControllerContract<NewUserDTO, StandardResponse> {
    @Autowired
    private NewUserUsecase useCase;

    @Override
    @PostMapping("/new/")
    public ResponseEntity<StandardResponse> perform(@Valid @RequestBody NewUserDTO dto) {
        return useCase.run(dto);
    }
}
