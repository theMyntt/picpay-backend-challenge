package com.themyntt.challenges.picpay.domain.core;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class EntityRoot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @Column(name = "createdAt")
    protected Date createdAt;

    @Column(name = "updatedAt")
    protected Date updatedAt;
}
