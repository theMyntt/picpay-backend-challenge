package com.themyntt.challenges.picpay.infrastructure.controllers;

import com.github.javafaker.Faker;
import com.themyntt.challenges.picpay.domain.enums.UserType;
import com.themyntt.challenges.picpay.domain.models.LoginUserResponseModel;
import com.themyntt.challenges.picpay.infrastructure.dtos.LoginUserDTO;
import com.themyntt.challenges.picpay.infrastructure.entities.UserEntity;
import com.themyntt.challenges.picpay.infrastructure.repositories.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class LoginUserControllerTest {
    private final Faker faker;
    private UserEntity entity;

    @Autowired
    private IUserRepository repository;

    @Autowired
    private LoginUserController controller;

    public LoginUserControllerTest() {
        this.faker = new Faker();
    }

    @BeforeEach
    void setup() {
        entity = UserEntity.builder()
                .cpf("887.894.360-67") // generated cpf
                .name(faker.name().fullName())
                .email(faker.internet().emailAddress())
                .type(UserType.USER)
                .password(faker.internet().password())
                .savedValue(50)
                .token(faker.internet().uuid())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();

        repository.save(entity);
    }

    @Test
    void perform() throws Exception {
        LoginUserDTO dto = LoginUserDTO.builder()
                .email(this.entity.getEmail())
                .password(this.entity.getPassword())
                .build();

        ResponseEntity<LoginUserResponseModel> response = controller.perform(dto);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertTrue(response.hasBody());

        if (response.getBody() == null) {
            throw new Exception("No response");
        }

        assertEquals(response.getBody().getUser().getName(), entity.getName());
        assertEquals(response.getBody().getUser().getToken(), entity.getToken());

        Optional<UserEntity> user = repository.findByEmailAndPassword(dto.getEmail(), dto.getPassword());

        if (user.isEmpty()) {
            throw new Exception("No user at databaese");
        }

        assertEquals(entity.getCpf(), user.get().getCpf());
        assertEquals(entity.getSavedValue(), 50);
        assertEquals(entity.getCpf(), user.get().getCpf());
        assertEquals(entity.getType(), UserType.USER);
    }
}