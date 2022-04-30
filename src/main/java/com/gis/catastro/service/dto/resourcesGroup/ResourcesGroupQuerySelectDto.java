package com.gis.catastro.service.dto.resourcesGroup;

import lombok.Data;

import java.util.Date;

@Data
public class ResourcesGroupQuerySelectDto {
    private String title;
    private String icon;
    private int deploymentOrder;
    public ResourcesGroupQuerySelectDto(String title, String icon, int deploymentOrder){
        this.title = title;
        this.icon = icon;
        this.deploymentOrder = deploymentOrder;
    }
}
