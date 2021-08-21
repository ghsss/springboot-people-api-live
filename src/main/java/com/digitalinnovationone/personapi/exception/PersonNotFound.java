package com.digitalinnovationone.personapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PersonNotFound extends Exception {
    public PersonNotFound (Long id) {
        super(String.format("Person not found with ID: %d", id));
    }
}
