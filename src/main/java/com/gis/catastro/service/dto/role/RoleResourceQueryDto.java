package com.gis.catastro.service.dto.role;

import lombok.Data;

@Data
public class RoleResourceQueryDto {
    private Long id;
    private String title;
    private boolean assign;
}
