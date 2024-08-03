package com.themyntt.challenges.picpay.infrastructure.controllers;

import com.github.javafaker.Faker;
import com.themyntt.challenges.picpay.domain.core.StandardResponse;
import com.themyntt.challenges.picpay.domain.enums.UserType;
import com.themyntt.challenges.picpay.infrastructure.dtos.NewUserDTO;
import com.themyntt.challenges.picpay.infrastructure.entities.UserEntity;
import com.themyntt.challenges.picpay.infrastructure.repositories.IUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class NewUserControllerTest {

    @Autowired
    private NewUserController controller;

    @Autowired
    private IUserRepository repository;

    private final NewUserDTO dto;

    public NewUserControllerTest() {
        Faker faker = new Faker();
        dto = NewUserDTO.builder()
                .cpf("88789436067") // generated cpf
                .email(faker.internet().emailAddress())
                .name(faker.name().fullName())
                .password(faker.internet().password())
                .type(UserType.USER)
                .build();
    }

    @Test
    @DisplayName("Should create user")
    void perform() throws Exception {
        ResponseEntity<StandardResponse> response = controller.perform(this.dto);

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertTrue(response.hasBody());

        Optional<UserEntity> user = repository.findByEmailAndPassword(this.dto.getEmail(), this.dto.getPassword());

        if (user.isEmpty()) {
            throw new Exception("User is null");
        }

        assertEquals(user.get().getType(), UserType.USER);
        assertEquals(user.get().getCpf(), dto.getCpf());
        assertEquals(user.get().getName(), dto.getName().toUpperCase());
        assertEquals(user.get().getPassword(), dto.getPassword());
        assertEquals(user.get().getEmail(), dto.getEmail());
    }
}