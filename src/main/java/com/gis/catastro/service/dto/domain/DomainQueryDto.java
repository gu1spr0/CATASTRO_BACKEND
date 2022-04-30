package com.gis.catastro.service.dto.domain;

import lombok.Data;
import java.util.Date;

@Data
public class DomainQueryDto {
    private Long id;
    private String typeDomain;
    private String codeDomain;
    private String nameDomain;
    private String descriptionDomain;
    private Date createdDate;
    private Long createdBy;
    private String state;
}
