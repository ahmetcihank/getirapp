package org.getir.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.Map;

@RequiredArgsConstructor
@Data
public class BadRequestExceptionPayload {
    private final HttpStatus httpStatus;
    private final Date date;
    private final String message;
    private final Map<String, String> errors;

}
