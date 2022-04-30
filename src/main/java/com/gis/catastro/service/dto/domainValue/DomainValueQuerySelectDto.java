package com.gis.catastro.service.dto.domainValue;

import lombok.Data;

@Data
public class DomainValueQuerySelectDto {
    private Long id;
    private String codeValue;
    private String titleDescription;
    private String charValue;
    private Long numericValue;
    private String charValueExtra;
    private Long numericValueExtra;
}
