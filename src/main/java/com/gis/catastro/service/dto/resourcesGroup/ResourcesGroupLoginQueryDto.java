package com.gis.catastro.service.dto.resourcesGroup;

import com.gis.catastro.service.dto.resourceLogin.ResourceLoginQueryDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ResourcesGroupLoginQueryDto {
    private String title;
    private String icon;
    private int deploymentOrder;
    private List<ResourceLoginQueryDto> resourceLoginQueryDtoList;

    public ResourcesGroupLoginQueryDto(){}

    public ResourcesGroupLoginQueryDto(String title, String icon, int deploymentOrder){
        this.title = title;
        this.icon = icon;
        this.deploymentOrder = deploymentOrder;
    }
}
