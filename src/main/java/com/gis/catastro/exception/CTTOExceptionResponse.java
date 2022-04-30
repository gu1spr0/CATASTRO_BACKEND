package com.gis.catastro.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CTTOExceptionResponse {
    private String code;
    private String message;
    private String details;

    public CTTOExceptionResponse(String pCode, String pMessage, String pDetails) {
        super();
        this.code = pCode;
        this.message = pMessage;
        this.details = pDetails;
    }
}
