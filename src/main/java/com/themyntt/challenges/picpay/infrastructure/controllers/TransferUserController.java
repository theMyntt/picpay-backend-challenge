package com.themyntt.challenges.picpay.infrastructure.controllers;

import com.themyntt.challenges.picpay.application.usecases.TransferUserUsecase;
import com.themyntt.challenges.picpay.domain.contracts.IControllerContract;
import com.themyntt.challenges.picpay.domain.core.StandardResponse;
import com.themyntt.challenges.picpay.infrastructure.dtos.TransferUserDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/v1/")
public class TransferUserController implements IControllerContract<TransferUserDTO, StandardResponse> {
    @Autowired
    private TransferUserUsecase useCase;

    @Override
    @PostMapping("/transfer/")
    public ResponseEntity<StandardResponse> perform(@Valid @RequestBody TransferUserDTO dto) {
        return useCase.run(dto);
    }
}
