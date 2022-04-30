package com.gis.catastro.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CTTOBadRequestException extends RuntimeException{
    @Getter
    private String code;

    public CTTOBadRequestException(String pCode) {
        super("Bad request -> personalizar el mensaje");
        this.code = pCode;
    }

    public CTTOBadRequestException(String pCode, String pMessage) {
        super(pMessage);
        this.code = pCode;
    }
}
