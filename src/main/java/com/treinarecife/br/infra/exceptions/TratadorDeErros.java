package com.treinarecife.br.infra.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroTratado tratarErroGenerico(Exception ex) {
        return new ErroTratado(
                "Ocorreu um erro interno no servidor.",
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        // ex.getMessage(),
        // ex.getStackTrace().toString()
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroTratado tratarErroNotFound(EntityNotFoundException ex) {
        // StringWriter sw = new StringWriter();
        // PrintWriter pw = new PrintWriter(sw);
        // ex.printStackTrace(pw);
        // String stackTraceString = sw.toString();
        return new ErroTratado(
                "Objeto não encontrado.",
                HttpStatus.NOT_FOUND.value());
        // ex.getMessage(),
        // stackTraceString);
    }
}
