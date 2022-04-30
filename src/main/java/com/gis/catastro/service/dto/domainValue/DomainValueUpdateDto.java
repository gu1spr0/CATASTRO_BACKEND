package com.gis.catastro.service.dto.domainValue;

import lombok.Data;

@Data
public class DomainValueUpdateDto {
    private String codeValue;
    private String titleDescription;
    private String charValue;
    private Long numericValue;
    private String charValueExtra;
    private Long numericValueExtra;
    private Long idDomain;
    private String state;
}
