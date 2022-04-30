package com.gis.catastro.service.dto.connection;

import lombok.Data;

import java.util.List;

@Data
public class ConnectQueryPageableDto {
    private List<ConnectionQueryDto> connectionQueryDtoList;
    private long totalRows;
}
