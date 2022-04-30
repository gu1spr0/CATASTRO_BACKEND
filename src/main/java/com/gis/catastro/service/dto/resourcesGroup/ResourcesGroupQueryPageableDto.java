package com.gis.catastro.service.dto.resourcesGroup;

import lombok.Data;

import java.util.List;

@Data
public class ResourcesGroupQueryPageableDto {
    private List<ResourcesGroupQueryDto> resourcesGroupQueryDtoList;
    private long totalRows;
}
