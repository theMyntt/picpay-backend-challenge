package com.themyntt.challenges.picpay.domain.core;

import lombok.Data;

import java.util.Date;

@Data
public class AggregateRoot {
    protected int id;
    protected Date createdAt;
    protected Date updatedAt;
}
