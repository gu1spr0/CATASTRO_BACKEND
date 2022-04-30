package com.gis.catastro.service.dto.domain;

import lombok.Data;

import java.util.Date;

@Data
public class DomainUpdateDto {
    private String typeDomain;
    private String codeDomain;
    private String nameDomain;
    private String descriptionDomain;
    private String state;
}
