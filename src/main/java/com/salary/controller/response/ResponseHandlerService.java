package com.salary.controller.response;

import com.salary.repository.entity.EmployerDTO;
import com.salary.repository.entity.PositionDTO;
import org.glassfish.jersey.internal.util.Producer;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
public class ResponseHandlerService {
    public Optional<String> invokeOrErrorMessage(boolean condition, Runnable logic, String errorMessage) {
        return handleException(() -> {
            if (condition) {
                logic.run();
                return Optional.empty();
            } else {
                return Optional.of(errorMessage);
            }
        });
    }

    public Optional<String> addOrUpdateOrErrorMessage(boolean condition, Runnable logic, Runnable logic1) {
        return handleException(() -> {
            if (condition) {
                logic.run();
            } else {
                logic1.run();
            }
            return Optional.empty();
        });
    }

    private Optional<String> handleException(Producer<Optional<String>> logic) {
        try {
            return logic.call();
        } catch (Exception e) {
            return Optional.of(e.getMessage());
        }
    }
}
