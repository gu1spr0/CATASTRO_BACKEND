package com.gis.catastro.service.dto.role;

import lombok.Data;

import java.util.List;

@Data
public class RoleResourceGroupQueryDto {
    private String title;
    private List<RoleResourceQueryDto> roleResourceQueryDtoList;
}
