package com.themyntt.challenges.picpay.infrastructure.controllers;

import com.github.javafaker.Faker;
import com.themyntt.challenges.picpay.domain.enums.UserType;
import com.themyntt.challenges.picpay.domain.models.DepositUserResponseModel;
import com.themyntt.challenges.picpay.infrastructure.dtos.DepositUserDTO;
import com.themyntt.challenges.picpay.infrastructure.entities.UserEntity;
import com.themyntt.challenges.picpay.infrastructure.repositories.IUserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DepositUserControllerTest {

    @Autowired
    private DepositUserController controller;

    @Autowired
    private IUserRepository repository;

    private UserEntity user;
    private UserEntity shopkeeper;
    private final Faker faker = new Faker();

    @BeforeEach
    void setup() {
        repository.deleteAll();

        this.user = UserEntity.builder()
                .cpf("88789436067") // generated cpf
                .name(faker.name().fullName())
                .email(faker.internet().emailAddress())
                .type(UserType.USER)
                .password(faker.internet().password())
                .savedValue(0)
                .token(faker.internet().uuid())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();

        this.shopkeeper = UserEntity.builder()
                .cpf("13802694074") // generated cpf
                .name(faker.name().fullName())
                .email(faker.internet().emailAddress())
                .type(UserType.SHOPKEEPER)
                .password(faker.internet().password())
                .savedValue(0)
                .token(faker.internet().uuid())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();

        repository.save(user);
        repository.save(shopkeeper);
    }

    @Test
    @DisplayName("Should deposit user money")
    void performCase1() throws Exception {
        Optional<UserEntity> entity = repository.findByEmailAndPassword(user.getEmail(), user.getPassword());

        if (entity.isEmpty()) {
            throw new Exception("No user in database");
        }

        DepositUserDTO dto = DepositUserDTO.builder()
                .id(entity.get().getId())
                .value(50)
                .build();

        ResponseEntity<DepositUserResponseModel> response = controller.perform(dto);

        if (response.getBody() == null) {
            throw new Exception("No body");
        }

        assertTrue(response.hasBody());
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        entity = repository.findByEmailAndPassword(user.getEmail(), user.getPassword());

        if (entity.isEmpty()) {
            throw new Exception("No user/shopkeeper in database");
        }

        assertEquals(entity.get().getSavedValue(), 50);
    }

    @Test
    @DisplayName("Should block shopkeeper deposit money")
    void performCase2() throws Exception {
        Optional<UserEntity> entity = repository.findByEmailAndPassword(shopkeeper.getEmail(), shopkeeper.getPassword());

        if (entity.isEmpty()) {
            throw new Exception("No user in database");
        }

        DepositUserDTO dto = DepositUserDTO.builder()
                .id(entity.get().getId())
                .value(50)
                .build();

        ResponseEntity<DepositUserResponseModel> response = controller.perform(dto);

        assertTrue(response.hasBody());
        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);

        entity = repository.findByEmailAndPassword(shopkeeper.getEmail(), shopkeeper.getPassword());

        if (entity.isEmpty()) {
            throw new Exception("No user/shopkeeper in database");
        }

        assertEquals(entity.get().getSavedValue(), 0);
    }
}