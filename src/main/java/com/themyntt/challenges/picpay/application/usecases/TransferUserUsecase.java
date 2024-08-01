package com.themyntt.challenges.picpay.application.usecases;

import com.themyntt.challenges.picpay.application.mappers.UserMapper;
import com.themyntt.challenges.picpay.domain.aggregates.UserAggregate;
import com.themyntt.challenges.picpay.domain.contracts.IUsecaseContract;
import com.themyntt.challenges.picpay.domain.core.StandardResponse;
import com.themyntt.challenges.picpay.domain.enums.UserType;
import com.themyntt.challenges.picpay.domain.models.AuthorizeHttpResponseModel;
import com.themyntt.challenges.picpay.domain.models.TransferUserResponseModel;
import com.themyntt.challenges.picpay.infrastructure.dtos.TransferUserDTO;
import com.themyntt.challenges.picpay.infrastructure.entities.UserEntity;
import com.themyntt.challenges.picpay.infrastructure.http.AuthorizeTransferHttp;
import com.themyntt.challenges.picpay.infrastructure.http.NotifyTransferHttp;
import com.themyntt.challenges.picpay.infrastructure.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class TransferUserUsecase implements IUsecaseContract<TransferUserDTO, TransferUserResponseModel> {
    @Autowired
    private IUserRepository repository;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private AuthorizeTransferHttp authorizeTransfer;

    @Autowired
    private NotifyTransferHttp notifyTransfer;

    @Override
    public ResponseEntity<TransferUserResponseModel> run(TransferUserDTO dto) {
        Optional<UserEntity> optionalUser = repository.findById(dto.getPayer());
        Optional<UserEntity> optionalShopkeeper = repository.findById(dto.getPayee());

        if (optionalUser.isEmpty() || optionalShopkeeper.isEmpty()) {
            return new ResponseEntity<>(new TransferUserResponseModel("No user found", 404, false), HttpStatus.NOT_FOUND);
        }

        UserEntity userEntity = optionalUser.get();
        UserEntity shopkeeperEntity = optionalShopkeeper.get();

        if (userEntity.getType() == UserType.SHOPKEEPER) {
            return new ResponseEntity<>(new TransferUserResponseModel("You don't have permission to do this", 403, false), HttpStatus.FORBIDDEN);
        }

        UserAggregate userAggregate = mapper.toDomain(userEntity);
        UserAggregate shopkeeperAggregate = mapper.toDomain(shopkeeperEntity);

        double value = dto.getValue();

        if (value > userAggregate.getSavedValue()) {
            return new ResponseEntity<>(new TransferUserResponseModel("You don't have enough money for this operation", 400, false), HttpStatus.BAD_REQUEST);
        }

        AuthorizeHttpResponseModel response = authorizeTransfer.send();

        if (Objects.equals(response.getStatus(), "fail") || !response.getData().isAuthorization()) {
            return new ResponseEntity<>(new TransferUserResponseModel("You are not authorized for make this operation", 401, false), HttpStatus.UNAUTHORIZED);
        }

        userAggregate.setSavedValue(userAggregate.getSavedValue() - value);
        shopkeeperAggregate.setSavedValue(shopkeeperAggregate.getSavedValue() + value);

        Boolean isNotified = notifyTransfer.send();

        if (!isNotified) {
            return new ResponseEntity<>(new TransferUserResponseModel("Bad Gateway", 502, false), HttpStatus.BAD_GATEWAY);
        }

        repository.save(mapper.toPersistance(userAggregate));
        repository.save(mapper.toPersistance(shopkeeperAggregate));

        return new ResponseEntity<>(new TransferUserResponseModel("Success", 200, true), HttpStatus.OK);
    }
}
