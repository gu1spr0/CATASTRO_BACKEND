package com.gis.catastro.service.dto.role;

import lombok.Data;

import java.util.List;

@Data
public class RoleQueryPageableDto {
    private List<RoleQueryDto> roleQueryDtoList;
    private long totalRows;
}
