package com.themyntt.challenges.picpay.application.usecases;

import com.themyntt.challenges.picpay.domain.aggregates.UserAggregate;
import com.themyntt.challenges.picpay.domain.contracts.IMapperContract;
import com.themyntt.challenges.picpay.infrastructure.entities.UserEntity;

public class UserMapper implements IMapperContract<UserEntity, UserAggregate> {
    @Override
    public UserAggregate toDomain(UserEntity userEntity) {
        return new UserAggregate(userEntity.getId(), userEntity.getName(), userEntity.getCpf(), userEntity.getEmail(), userEntity.getPassword(), userEntity.getType(), userEntity.getCreatedAt(), userEntity.getUpdatedAt());
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

        return user;
    }
}
