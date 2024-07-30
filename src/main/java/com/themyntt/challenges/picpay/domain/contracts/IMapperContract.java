package com.themyntt.challenges.picpay.domain.contracts;

public interface IMapperContract<Entity, Aggregate> {
    Aggregate toDomain(Entity entity);
    Entity toPersistance(Aggregate entity);
}
