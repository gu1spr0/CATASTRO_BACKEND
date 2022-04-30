package com.gis.catastro.service.impl;

import com.gis.catastro.exception.Message;
import com.gis.catastro.exception.MessageDescription;
import com.gis.catastro.model.entity.Route;
import com.gis.catastro.model.repository.RouteRepository;
import com.gis.catastro.service.RouteService;
import com.gis.catastro.service.dto.route.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {
    @Autowired
    private RouteRepository routeRepository;

    @Override
    public RouteQueryPageableDto getRoutesPageable(int pPage, int pRowsNumber) {
        RouteQueryPageableDto vRouteQueryPageableDto = new RouteQueryPageableDto();
        Long vTotalRows = routeRepository.getCountRoutes();
        if (vTotalRows>0){
            List<Route> vRouteList = routeRepository.getRoutesPageable(PageRequest.of(pPage, pRowsNumber));
            List<RouteQueryDto> vRouteQueryDtoList = new ArrayList<>();
            for(Route vRoute: vRouteList){
                RouteQueryDto vRouteQueryDto = new RouteQueryDto();
                BeanUtils.copyProperties(vRoute, vRouteQueryDto);
                vRouteQueryDtoList.add(vRouteQueryDto);
            }
            vRouteQueryPageableDto.setTotalRows(vTotalRows);
            vRouteQueryPageableDto.setRouteQueryDtoList(vRouteQueryDtoList);
        }else{
            vRouteQueryPageableDto.setTotalRows(0);
        }
        return vRouteQueryPageableDto;
    }

    @Override
    public RouteQueryDto addRoute(RouteAddDto pRouteAddDto) {
        Route vRoute = new Route();
        BeanUtils.copyProperties(pRouteAddDto, vRoute);
        routeRepository.save(vRoute);
        RouteQueryDto vRouteQueryDto = new RouteQueryDto();
        BeanUtils.copyProperties(vRoute, vRouteQueryDto);
        return vRouteQueryDto;
    }

    @Override
    public RouteQueryDto updateRoute(long pRouteId, RouteUpdateDto pRouteUpdateDto) {
        Route vRoute = this.getRouteById(pRouteId);
        BeanUtils.copyProperties(pRouteUpdateDto, vRoute);
        routeRepository.save(vRoute);
        RouteQueryDto vRouteQueryDto = new RouteQueryDto();
        BeanUtils.copyProperties(vRoute, vRouteQueryDto);
        return vRouteQueryDto;
    }

    @Override
    public Route getRouteById(long pRouteId) {
        Route vRoute = routeRepository.getRouteById(pRouteId).orElse(null);
        if(vRoute == null){
            Object[] obj = {"route", "id", String.valueOf(pRouteId)};
            throw Message.GetBadRequest(MessageDescription.notExists, obj);
        }
        return vRoute;
    }

    @Override
    public List<RouteQuerySelectDto> getRoutes() {
        List<RouteQuerySelectDto> vRouteQuerySelectDtoList = new ArrayList<>();
        List<Route> routeList = routeRepository.getRoutes();
        for (Route vRoute: routeList){
            RouteQuerySelectDto vRouteQuerySelectDto = new RouteQuerySelectDto();
            vRouteQuerySelectDto.setId(vRoute.getId());
            vRouteQuerySelectDto.setDescription(vRoute.getDescription());
            vRouteQuerySelectDtoList.add(vRouteQuerySelectDto);
        }
        return vRouteQuerySelectDtoList;
    }
}
