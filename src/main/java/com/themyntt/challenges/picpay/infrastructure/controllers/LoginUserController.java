package com.themyntt.challenges.picpay.infrastructure.controllers;

import com.themyntt.challenges.picpay.application.usecases.LoginUserUsecase;
import com.themyntt.challenges.picpay.domain.contracts.IControllerContract;
import com.themyntt.challenges.picpay.domain.models.LoginUserResponseModel;
import com.themyntt.challenges.picpay.infrastructure.dtos.LoginUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/v1/")
public class LoginUserController implements IControllerContract<LoginUserDTO, LoginUserResponseModel> {
    @Autowired
    private LoginUserUsecase useCase;

    @Override
    @PostMapping("/login/")
    public ResponseEntity<LoginUserResponseModel> perform(@RequestBody LoginUserDTO dto) {
        return useCase.run(dto);
    }
}
