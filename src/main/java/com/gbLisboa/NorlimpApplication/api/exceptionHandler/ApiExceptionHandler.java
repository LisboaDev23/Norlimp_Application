package com.gbLisboa.NorlimpApplication.api.exceptionHandler;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid (MethodArgumentNotValidException ex,
                                                                   HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(statusCode);
        problemDetail.setTitle("Um ou mais campos estão inválidos!");
        problemDetail.setType(URI.create("https://norlimp.com/ajuda/campos-invalidos"));
        problemDetail.setDetail("Verifique se os campos que estão sendo requisitados foram preenchidos corretamente.");
        var fields = ex.getBindingResult().getAllErrors().stream()
                .collect(Collectors.toMap(error -> ((FieldError)error).getField(),
                        error -> messageSource.getMessage(error, LocaleContextHolder.getLocale())));
        problemDetail.setProperty("fields",fields);

        return super.handleExceptionInternal(ex, problemDetail, headers, statusCode, request);
    }
}
