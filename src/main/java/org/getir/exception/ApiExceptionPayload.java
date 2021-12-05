package org.getir.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;

@RequiredArgsConstructor
@Data
public class ApiExceptionPayload {
    private final HttpStatus httpStatus;
    private final Date date;
    private final String message;
}
