package com.gis.catastro.service.dto.resourcesGroup;

import lombok.Data;

@Data
public class ResourcesGroupUpdateDto {
    private String title;
    private String description;
    private String icon;
    private int deploymentOrder;
    private Long idRoute;
    private String state;
}
