package com.gis.catastro.controller;

import com.gis.catastro.model.entity.Property;
import com.gis.catastro.service.PropertyService;
import com.gis.catastro.service.dto.property.PropertyQueryDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(description = "Endpoints para la gestión predios")
@RestController
@RequestMapping("api/predios")
public class PropertyController {
    @Autowired
    private PropertyService propertyService;

    @ApiOperation(value = "Obtiene predio por geocodigo", authorizations = @Authorization(value = "Bearer"))
    @GetMapping(path = "/{geocodigo}")
    @ResponseStatus(HttpStatus.OK)
    public Property getPredioByGeocodigo(@ApiParam(value="Geocodigo de predio") @PathVariable("geocodigo") String geocodigo){
        return propertyService.getPredioByGeocodigo(geocodigo);
    }
}
