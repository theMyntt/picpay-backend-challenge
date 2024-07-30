package com.themyntt.challenges.picpay.domain.aggregates;

import com.themyntt.challenges.picpay.domain.core.AggregateRoot;
import com.themyntt.challenges.picpay.domain.enums.UserType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserAggregate extends AggregateRoot {
    private String name;
    private String cpf;
    private String email;
    private String password;
    private UserType type;

    public UserAggregate(String name, String cpf, String email, String password, UserType type) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
        this.type = type;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public UserAggregate(Long id, String name, String cpf, String email, String password, UserType type, Date createdAt) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = new Date();
    }

    public UserAggregate(Long id, String name, String cpf, String email, String password, UserType type, Date createdAt, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}