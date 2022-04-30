package com.gis.catastro.service.dto.role;

import lombok.Data;

import java.util.List;

@Data
public class RolePermissionGroupQueryDto {
    private String title;
    private String permissionGroupCode;
    private List<RolePermissionQueryDto> rolePermissionQueryDtoList;
}
