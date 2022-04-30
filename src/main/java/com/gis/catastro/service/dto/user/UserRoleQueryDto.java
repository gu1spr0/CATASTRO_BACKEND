package com.gis.catastro.service.dto.user;

import lombok.Data;

@Data
public class UserRoleQueryDto {
    private Long id;
    private String role;
    private String description;
    private boolean assigned;
}
