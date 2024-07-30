package com.themyntt.challenges.picpay.application.usecases;

import com.themyntt.challenges.picpay.application.mappers.UserMapper;
import com.themyntt.challenges.picpay.domain.contracts.IUsecaseContract;
import com.themyntt.challenges.picpay.domain.core.StandardResponse;
import com.themyntt.challenges.picpay.domain.models.LoginUserResponseModel;
import com.themyntt.challenges.picpay.domain.models.UserOmitStrictInfoModel;
import com.themyntt.challenges.picpay.infrastructure.dtos.LoginUserDTO;
import com.themyntt.challenges.picpay.infrastructure.entities.UserEntity;
import com.themyntt.challenges.picpay.infrastructure.repositories.IUserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginUserUsecase implements IUsecaseContract<LoginUserDTO, LoginUserResponseModel> {
    @Autowired
    private IUserRepository repository;

    @Autowired
    private UserMapper mapper;

    @Override
    public ResponseEntity<LoginUserResponseModel> run(LoginUserDTO dto) {
        try {
            Optional<UserEntity> optional = repository.findByEmailAndPassword(dto.getEmail(), dto.getPassword());

            if (optional.isEmpty()) {
                return new ResponseEntity<>(new LoginUserResponseModel("No user found", 404), HttpStatus.NOT_FOUND);
            }

            UserEntity entity = optional.get();

            UserOmitStrictInfoModel user = new UserOmitStrictInfoModel();

            user.setId(entity.getId());
            user.setName(entity.getName());

            LoginUserResponseModel response = new LoginUserResponseModel("User successfully logged", 200, user);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new LoginUserResponseModel("Internal server error", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
