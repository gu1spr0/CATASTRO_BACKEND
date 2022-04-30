package com.gis.catastro.service;

import com.gis.catastro.model.entity.Route;
import com.gis.catastro.service.dto.route.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RouteService {
    RouteQueryPageableDto getRoutesPageable(int pPage, int pRowsNumber);
    RouteQueryDto addRoute(RouteAddDto pRouteAddDto);
    RouteQueryDto updateRoute(long pRouteId, RouteUpdateDto pRouteUpdateDto);
    Route getRouteById(long pRouteId);
    List<RouteQuerySelectDto> getRoutes();
}
