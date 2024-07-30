package com.themyntt.challenges.picpay.infrastructure.dtos;

import com.themyntt.challenges.picpay.domain.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewUserDTO {
    private String name;
    private String cpf;
    private String email;
    private String password;
    private UserType type;
}
