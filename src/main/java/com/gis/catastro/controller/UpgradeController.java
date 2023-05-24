package com.gis.catastro.controller;

import com.gis.catastro.model.entity.Property;
import com.gis.catastro.model.entity.Upgrade;
import com.gis.catastro.service.UpgradeService;
import com.gis.catastro.service.dto.upgrade.UpgradeQueryDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "Endpoints para la gestión mejoras")
@RestController
@RequestMapping("api/mejoras")
public class UpgradeController {
    @Autowired
    private UpgradeService upgradeService;

    @ApiOperation(value = "Obtiene mejoras por geocodigo", authorizations = @Authorization(value = "Bearer"))
    @GetMapping(path = "/{geocodigo}")
    @ResponseStatus(HttpStatus.OK)
    public List<UpgradeQueryDto> getMejorasByGeocodigo(@ApiParam(value="Geocodigo de mejoras") @PathVariable("geocodigo") String geocodigo){
        return upgradeService.getUpgradesByGeocodigo(geocodigo);
    }
}
