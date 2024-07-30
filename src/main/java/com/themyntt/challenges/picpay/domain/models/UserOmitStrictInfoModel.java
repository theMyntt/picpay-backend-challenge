package com.themyntt.challenges.picpay.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOmitStrictInfoModel {
    private String name;
    private Long id;
}
