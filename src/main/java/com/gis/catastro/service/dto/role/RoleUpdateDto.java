package com.gis.catastro.service.dto.role;

import lombok.Data;

@Data
public class RoleUpdateDto {
    private String role;
    private String description;
    private String state;
}
