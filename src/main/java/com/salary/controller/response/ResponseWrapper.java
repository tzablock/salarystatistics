package com.salary.controller.response;

import com.salary.repository.entity.EmployerDTO;
import com.salary.repository.entity.PositionDTO;
import org.glassfish.jersey.internal.util.Producer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class ResponseWrapper { //TODO test
    private Optional<String> errorMessageOpt;
    private String errorMessage;

    public ResponseWrapper(String errorMessage) {
        this.errorMessageOpt = Optional.empty();
        this.errorMessage = errorMessage;
    }

    public ResponseWrapper invokeOnCondition(boolean condition, Runnable logic) {
        return handleException(() -> {
            if (condition) {
                logic.run();
                return Optional.empty();
            } else {
                return Optional.of(errorMessage);
            }
        });
    }

    public ResponseWrapper invokeOrAlternativeOnCondition(boolean condition, Runnable logic, Runnable logic1) {
        return handleException(() -> {
            if (condition) {
                logic.run();
            } else {
                logic1.run();
            }
            return Optional.empty();
        });
    }

    public ResponseEntity<String> httpFormat(String successMessage) {
        return errorMessageOpt.map(error -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error))
                              .orElse(ResponseEntity.ok(successMessage));
    }

    private ResponseWrapper handleException(Producer<Optional<String>> logic) {
        try {
            errorMessageOpt = logic.call();
        } catch (RuntimeException e) {
            errorMessageOpt = Optional.of(e.toString());
        }
        return this;
    }
}
