package com.gis.catastro.service.dto.resource;

import lombok.Data;

import java.util.List;

@Data
public class ResourceQueryPageableDto {
    private List<ResourceQueryDto> resourceQueryDtoList;
    private long totalRows;
}
