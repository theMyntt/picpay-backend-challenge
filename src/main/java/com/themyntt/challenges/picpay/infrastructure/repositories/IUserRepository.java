package com.themyntt.challenges.picpay.infrastructure.repositories;

import com.themyntt.challenges.picpay.infrastructure.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {
}
