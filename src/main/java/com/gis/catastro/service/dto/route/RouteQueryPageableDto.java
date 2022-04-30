package com.gis.catastro.service.dto.route;

import lombok.Data;

import java.util.List;

@Data
public class RouteQueryPageableDto {
    private List<RouteQueryDto> routeQueryDtoList;
    private long totalRows;
}
