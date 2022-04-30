package com.gis.catastro.service.dto.role;

import lombok.Data;
import java.util.Date;

@Data
public class RoleQueryDto {
    private Long id;
    private String role;
    private String description;
    private Date createdDate;
    private Long createdBy;
    private String state;
}
