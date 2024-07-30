package com.themyntt.challenges.picpay.application.usecases;

import com.themyntt.challenges.picpay.application.mappers.UserMapper;
import com.themyntt.challenges.picpay.domain.aggregates.UserAggregate;
import com.themyntt.challenges.picpay.domain.contracts.IUsecaseContract;
import com.themyntt.challenges.picpay.domain.core.StandardResponse;
import com.themyntt.challenges.picpay.domain.enums.UserType;
import com.themyntt.challenges.picpay.domain.models.DepositUserResponseModel;
import com.themyntt.challenges.picpay.infrastructure.dtos.DepositUserDTO;
import com.themyntt.challenges.picpay.infrastructure.entities.UserEntity;
import com.themyntt.challenges.picpay.infrastructure.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepositUserUsecase implements IUsecaseContract<DepositUserDTO, DepositUserResponseModel> {
    @Autowired
    private IUserRepository repository;

    @Autowired
    private UserMapper mapper;

    @Override
    public ResponseEntity<DepositUserResponseModel> run(DepositUserDTO dto) {
        Optional<UserEntity> optional = repository.findById(dto.getId());

        if (optional.isEmpty()) {
            return new ResponseEntity<>(new DepositUserResponseModel("No user found", 404), HttpStatus.NOT_FOUND);
        }

        UserAggregate aggregate = mapper.toDomain(optional.get());

        if (aggregate.getType() == UserType.SHOPKEEPER) {
            return new ResponseEntity<>(new DepositUserResponseModel("You don't have permission to do this", 403), HttpStatus.FORBIDDEN);
        }

        if (dto.getValue() < 0 && Math.abs(dto.getValue()) > aggregate.getSavedValue()) {
            return new ResponseEntity<>(new DepositUserResponseModel("You don't have enough money for this operation", 400), HttpStatus.BAD_REQUEST);
        }

        aggregate.setSavedValue(aggregate.getSavedValue() + dto.getValue());

        repository.save(mapper.toPersistance(aggregate));

        return new ResponseEntity<>(new DepositUserResponseModel("Updated successfully", 200, aggregate.getSavedValue()), HttpStatus.OK);
    }
}
