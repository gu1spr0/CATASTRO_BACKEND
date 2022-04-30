package com.gis.catastro.service.dto.resourcesGroup;

import lombok.Data;

@Data
public class ResourcesGroupAddDto {
    private String title;
    private String description;
    private String icon;
    private int deploymentOrder;
    private Long idRoute;
}
