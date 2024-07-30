package com.themyntt.challenges.picpay.infrastructure.entities;

import com.themyntt.challenges.picpay.domain.core.EntityRoot;
import com.themyntt.challenges.picpay.domain.enums.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity extends EntityRoot {
    @Column(name = "name")
    private String name;

    @Column(name = "cpf", unique = true)
    private String cpf;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "type")
    private UserType type;
}
