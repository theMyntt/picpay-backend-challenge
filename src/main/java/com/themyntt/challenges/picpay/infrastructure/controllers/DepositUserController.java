package com.themyntt.challenges.picpay.infrastructure.controllers;

import com.themyntt.challenges.picpay.application.usecases.DepositUserUsecase;
import com.themyntt.challenges.picpay.domain.contracts.IControllerContract;
import com.themyntt.challenges.picpay.domain.core.StandardResponse;
import com.themyntt.challenges.picpay.domain.models.DepositUserResponseModel;
import com.themyntt.challenges.picpay.infrastructure.dtos.DepositUserDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/v1/")
public class DepositUserController implements IControllerContract<DepositUserDTO, DepositUserResponseModel> {
    @Autowired
    private DepositUserUsecase useCase;

    @Override
    @PostMapping("/deposit/")
    public ResponseEntity<DepositUserResponseModel> perform(@Valid @RequestBody DepositUserDTO dto) {
        return useCase.run(dto);
    }
}
