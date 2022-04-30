package com.gis.catastro.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CTTONotFoundException extends RuntimeException{
    @Getter
    private String code;

    public CTTONotFoundException(Long pId, String pEntity, String pCode) {
        super("No se encontró resultados para la consulta de " + pEntity + " con el Id: " + pId);
        this.code = pCode;
    }

    public CTTONotFoundException(String pCode, String pMessage) {
        super(pMessage);
        this.code = pCode;
    }
}
