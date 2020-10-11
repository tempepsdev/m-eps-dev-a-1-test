package com.mutualser.developer.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.mutualser.developer.application.common.util.Utility;
import com.mutualser.developer.exceptions.data.ErrorResponse;
import com.mutualser.developer.exceptions.moneybox.MoneyBoxConflictException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GeneralExceptionHandler {
  @ExceptionHandler({InvalidFormatException.class, HttpMessageNotReadableException.class})
  @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
  public ErrorResponse tryingExceptionType(InvalidFormatException exceptionToCatch) {
    generatePersonalizedLog(exceptionToCatch);
    final String invalidParameter = exceptionToCatch.getPathReference().split("\"")[1];
    return ErrorResponse.builder()
        .id(Utility.generateRandomId())
        .message(
            String.format(
                "No se reconoce el parametro '%s' dentro del atributo %s.",
                exceptionToCatch.getValue(), invalidParameter))
        .build();
  }

  @ExceptionHandler(MoneyBoxConflictException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ErrorResponse tryingExceptionType(MoneyBoxConflictException exceptionToCatch) {
    generatePersonalizedLog(exceptionToCatch);
    return ErrorResponse.builder()
        .id(Utility.generateRandomId())
        .message(exceptionToCatch.getMessage())
        .build();
  }

  private void generatePersonalizedLog(Exception exceptionToCatch) {
    log.error(
        String.format(
            "[GeneralExceptionHandler] ::: %s ::: [GeneralExceptionHandler]",
            exceptionToCatch.getMessage()));
  }
}
