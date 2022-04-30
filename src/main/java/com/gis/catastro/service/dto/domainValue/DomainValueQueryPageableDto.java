package com.gis.catastro.service.dto.domainValue;

import lombok.Data;

import java.util.List;

@Data
public class DomainValueQueryPageableDto {
    private List<DomainValueQueryDto> domainValueQueryDtoList;
    private long totalRows;
}
