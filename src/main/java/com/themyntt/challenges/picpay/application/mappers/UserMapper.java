package com.themyntt.challenges.picpay.application.mappers;

import com.themyntt.challenges.picpay.domain.aggregates.UserAggregate;
import com.themyntt.challenges.picpay.domain.contracts.IMapperContract;
import com.themyntt.challenges.picpay.infrastructure.entities.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class UserMapper implements IMapperContract<UserEntity, UserAggregate> {
    @Override
    public UserAggregate toDomain(UserEntity userEntity) {
        return new UserAggregate(userEntity.getId(), userEntity.getName(), userEntity.getCpf(), userEntity.getEmail(), userEntity.getPassword() ,userEntity.getType(), userEntity.getSavedValue(), userEntity.getToken(), userEntity.getCreatedAt(), userEntity.getUpdatedAt());
    }

    @Override
    public UserEntity toPersistance(UserAggregate entity) {
        UserEntity user = new UserEntity();

        user.setId(entity.getId());
        user.setType(entity.getType());
        user.setName(entity.getName());
        user.setCpf(entity.getCpf());
        user.setEmail(entity.getEmail());
        user.setPassword(entity.getPassword());
        user.setCreatedAt(entity.getCreatedAt());
        user.setUpdatedAt(entity.getUpdatedAt());
        user.setSavedValue(entity.getSavedValue());
        user.setToken(entity.getToken());

        return user;
    }
}
