package com.gis.catastro.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CTTOResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    String message = "";

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception pEx, WebRequest pRequest){
        CTTOExceptionResponse exceptionResponse = new CTTOExceptionResponse("0", pEx.getMessage(), pRequest.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CTTONotFoundException.class)
    public final ResponseEntity<Object> handleServerNotFoundException(CTTONotFoundException pEx, WebRequest pRequest){
        CTTOExceptionResponse exceptionResponse =
                new CTTOExceptionResponse(pEx.getCode(), pEx.getMessage(), pEx.getStackTrace().toString());
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CTTOBadRequestException.class)
    public final ResponseEntity<Object> handleServerBadRequestException(CTTOBadRequestException pEx, WebRequest pRequest){
        CTTOExceptionResponse exceptionResponse =
                new CTTOExceptionResponse(pEx.getCode(), pEx.getMessage(), pEx.getStackTrace().toString());
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException pEx, HttpHeaders pHeaders, HttpStatus pStatus, WebRequest pRequest) {
        pEx.getBindingResult().getAllErrors().forEach(e -> {
            message += e.getDefaultMessage().toString() + "/n";
        });
        CTTOExceptionResponse exceptionResponse = new CTTOExceptionResponse(pEx.getParameter().toString(), message, pEx.getBindingResult().toString());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
