package com.themyntt.challenges.picpay.domain.core;

import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class EntityRoot {
    @Id
    @Column(name = "id")
    protected int id;

    @Column(name = "createdAt")
    protected Date createdAt;

    @Column(name = "updatedAt")
    protected Date updatedAt;
}
