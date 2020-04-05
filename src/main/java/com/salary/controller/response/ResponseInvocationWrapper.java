package com.salary.controller.response;

import org.glassfish.jersey.internal.util.Producer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class ResponseInvocationWrapper { //TODO test
    private Optional<String> errorMessageOpt;
    private String errorMessage;

    public ResponseInvocationWrapper(String errorMessage) {
        this.errorMessageOpt = Optional.empty();
        this.errorMessage = errorMessage;
    }

    public ResponseInvocationWrapper invokeOnCondition(boolean condition, Runnable logic) {
        return handleException(() -> {
            if (condition) {
                logic.run();
                return Optional.empty();
            } else {
                return Optional.of(errorMessage);
            }
        });
    }

    public ResponseInvocationWrapper invokeOrAlternativeOnCondition(boolean condition, Runnable logic, Runnable logic1) {
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

    private ResponseInvocationWrapper handleException(Producer<Optional<String>> logic) {
        try {
            errorMessageOpt = logic.call();
        } catch (RuntimeException e) {
            errorMessageOpt = Optional.of(e.toString());
        }
        return this;
    }
}
