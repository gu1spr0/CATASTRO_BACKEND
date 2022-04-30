package com.gis.catastro.service.dto.domainValue;

import lombok.Data;
import java.util.Date;

@Data
public class DomainValueQueryDto {
    private Long id;
    private Date createdDate;
    private Long createdBy;
    private String codeValue;
    private String titleDescription;
    private String charValue;
    private Long numericValue;
    private String charValueExtra;
    private Long numericValueExtra;
    private Long idDomain;
    private String state;
}
