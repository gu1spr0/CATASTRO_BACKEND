package com.gis.catastro.service.dto.resource;

import lombok.Data;
import java.util.Date;

@Data
public class ResourceQueryDto {
    private Long id;
    private String title;
    private String description;
    private int deploymentOrder;
    private Long idResourcesGroup;
    private Long idRoute;
    private Date createdDate;
    private Long createdBy;
    private String state;
}
