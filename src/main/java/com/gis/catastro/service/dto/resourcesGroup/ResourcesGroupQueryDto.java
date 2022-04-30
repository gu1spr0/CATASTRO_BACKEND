package com.gis.catastro.service.dto.resourcesGroup;

import lombok.Data;
import java.util.Date;

@Data
public class ResourcesGroupQueryDto {
    private Long id;
    private String title;
    private String description;
    private String icon;
    private int deploymentOrder;
    private Long idRoute;
    private Date createdDate;
    private Long createdBy;
    private String state;
}
