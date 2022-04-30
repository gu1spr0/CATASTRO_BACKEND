package com.gis.catastro.service.dto.domain;

import lombok.Data;

import java.util.List;

@Data
public class DomainQueryPageableDto {
    private List<DomainQueryDto> domainQueryDtoList;
    private long totalRows;
}
