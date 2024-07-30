package com.themyntt.challenges.picpay.application.usecases;

import com.themyntt.challenges.picpay.application.mappers.UserMapper;
import com.themyntt.challenges.picpay.domain.aggregates.UserAggregate;
import com.themyntt.challenges.picpay.domain.contracts.IUsecaseContract;
import com.themyntt.challenges.picpay.domain.core.StandardResponse;
import com.themyntt.challenges.picpay.domain.enums.UserType;
import com.themyntt.challenges.picpay.infrastructure.dtos.TransferUserDTO;
import com.themyntt.challenges.picpay.infrastructure.entities.UserEntity;
import com.themyntt.challenges.picpay.infrastructure.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransferUserUsecase implements IUsecaseContract<TransferUserDTO, StandardResponse> {
    @Autowired
    private IUserRepository repository;

    @Autowired
    private UserMapper mapper;

    @Override
    public ResponseEntity<StandardResponse> run(TransferUserDTO dto) {
        Optional<UserEntity> optionalUser = repository.findById(dto.getPayer());
        Optional<UserEntity> optionalShopkeeper = repository.findById(dto.getPayee());

        if (optionalUser.isEmpty() || optionalShopkeeper.isEmpty()) {
            return new ResponseEntity<>(new StandardResponse("No user found", 404), HttpStatus.NOT_FOUND);
        }

        UserEntity userEntity = optionalUser.get();
        UserEntity shopkeeperEntity = optionalShopkeeper.get();

        if (userEntity.getType() == UserType.SHOPKEEPER) {
            return new ResponseEntity<>(new StandardResponse("You don't have permission to do this", 403), HttpStatus.FORBIDDEN);
        }

        UserAggregate userAggregate = mapper.toDomain(userEntity);
        UserAggregate shopkeeperAggregate = mapper.toDomain(shopkeeperEntity);

        double value = dto.getValue();

        if (value > userAggregate.getSavedValue()) {
            return new ResponseEntity<>(new StandardResponse("You don't have enough money for this operation", 400), HttpStatus.BAD_REQUEST);
        }

        userAggregate.setSavedValue(userAggregate.getSavedValue() - value);
        shopkeeperAggregate.setSavedValue(shopkeeperAggregate.getSavedValue() + value);

        repository.save(mapper.toPersistance(userAggregate));
        repository.save(mapper.toPersistance(shopkeeperAggregate));

        return new ResponseEntity<>(new StandardResponse("Successfull", 200), HttpStatus.OK);
    }
}
