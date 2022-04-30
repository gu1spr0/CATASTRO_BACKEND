package com.gis.catastro.controller;

import com.gis.catastro.service.RouteService;
import com.gis.catastro.service.dto.route.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(description = "Endpoints para la gestión de rutas")
@RestController
@RequestMapping("api/routes")
public class RouteController {
    private final RouteService routeService;
    public RouteController(RouteService routeService){
        this.routeService = routeService;
    }

    @ApiOperation(value = "Obtiene un listado de las rutas paginadas", authorizations = @Authorization(value = "Bearer"))
    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public RouteQueryPageableDto getRoutePageable(@ApiParam(value = "Número de página a consultar") @RequestParam int page,
                                                  @ApiParam(value = "Número de registros a consultar") @RequestParam int rowsNumber){
        return routeService.getRoutesPageable(page,rowsNumber);
    }

    @ApiOperation(value = "Obtiene un listado de las rutas para select", authorizations = @Authorization(value = "Bearer"))
    @GetMapping(path = "select")
    @ResponseStatus(HttpStatus.OK)
    public List<RouteQuerySelectDto> getRouteSelect(){
        return routeService.getRoutes();
    }

    @ApiOperation(value = "Crea una ruta", authorizations = @Authorization(value = "Bearer"))
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public RouteQueryDto addRoute(@ApiParam(value = "Datos de la ruta") @Valid @RequestBody RouteAddDto routeAddDto){
        return routeService.addRoute(routeAddDto);
    }

    @ApiOperation(value = "Actualiza la información de una ruta", authorizations = @Authorization(value = "Bearer"))
    @PutMapping(path = "{routeId}")
    @ResponseStatus(HttpStatus.OK)
    public RouteQueryDto updateRole(@ApiParam(value = "Identificador de la ruta") @PathVariable Long routeId,
                                    @ApiParam(value = "Datos a actualizar") @Valid @RequestBody RouteUpdateDto routeUpdateDto){
        return routeService.updateRoute(routeId, routeUpdateDto);
    }
}
