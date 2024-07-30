package com.themyntt.challenges.picpay.application.usecases;

import com.themyntt.challenges.picpay.application.mappers.UserMapper;
import com.themyntt.challenges.picpay.application.utils.CPFValidator;
import com.themyntt.challenges.picpay.domain.aggregates.UserAggregate;
import com.themyntt.challenges.picpay.domain.contracts.IUsecaseContract;
import com.themyntt.challenges.picpay.domain.core.StandardResponse;
import com.themyntt.challenges.picpay.infrastructure.dtos.NewUserDTO;
import com.themyntt.challenges.picpay.infrastructure.entities.UserEntity;
import com.themyntt.challenges.picpay.infrastructure.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class NewUserUsecase implements IUsecaseContract<NewUserDTO, StandardResponse> {
    @Autowired
    private IUserRepository repository;

    @Autowired
    private UserMapper mapper;

    @Override
    public ResponseEntity<StandardResponse> run(NewUserDTO dto) {
        try {
            String cpf = CPFValidator.validate(dto.getCpf());
            String name = dto.getName().trim().replaceAll("\\s+", " ").toUpperCase();

            UserAggregate aggregate = new UserAggregate(name, cpf, dto.getEmail().toLowerCase(), dto.getPassword(), dto.getType(), 0);

            UserEntity entity = mapper.toPersistance(aggregate);

            repository.save(entity);

            return new ResponseEntity<>(new StandardResponse("User successfully created", 200), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(new StandardResponse("User already exists", 409), HttpStatus.CONFLICT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new StandardResponse("Invalid CPF", 400), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new StandardResponse("Internal server error", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
