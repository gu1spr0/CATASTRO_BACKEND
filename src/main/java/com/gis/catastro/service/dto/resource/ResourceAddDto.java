package com.gis.catastro.service.dto.resource;

import lombok.Data;

@Data
public class ResourceAddDto {
    private String title;
    private String description;
    private int deploymentOrder;
    private Long idResourcesGroup;
    private Long idRoute;
}
