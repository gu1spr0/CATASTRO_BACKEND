package com.gis.catastro.service.dto.role;

import lombok.Data;

@Data
public class RolePermissionQueryDto {
    private Long id;
    private String title;
    private String permissionCode;
    private boolean assign;
}
