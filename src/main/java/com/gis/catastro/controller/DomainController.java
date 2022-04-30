package com.gis.catastro.controller;

import com.gis.catastro.service.DomainService;
import com.gis.catastro.service.dto.domain.DomainAddDto;
import com.gis.catastro.service.dto.domain.DomainQueryDto;
import com.gis.catastro.service.dto.domain.DomainQueryPageableDto;
import com.gis.catastro.service.dto.domain.DomainUpdateDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(description = "Endpoints para la gestión de dominios")
@RestController
@RequestMapping("api/domains")
public class DomainController {
    private final DomainService domainService;
    public DomainController(DomainService domainService){
        this.domainService = domainService;
    }

    @ApiOperation(value = "Obtiene un listado de dominios paginados por estado", authorizations = @Authorization(value = "Bearer"))
    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public DomainQueryPageableDto getDomainPageable(@ApiParam(value = "Estado del dominio a filtrar") @RequestParam String state,
                                                    @ApiParam(value = "Número de página a consultar") @RequestParam int page,
                                                    @ApiParam(value = "Número de registros a consultar") @RequestParam int rowsNumber){
        return domainService.getDomainPageable(state, page, rowsNumber);
    }

    @ApiOperation(value = "Obtiene un listado de dominios paginados filtrado por estado", authorizations = @Authorization(value = "Bearer"))
    @GetMapping(path = "find")
    @ResponseStatus(HttpStatus.OK)
    public DomainQueryPageableDto findUsersPageable(@ApiParam(value = "Valor para filtrar") @RequestParam String criteria,
                                                    @ApiParam(value = "Estado del usuario a filtrar") @RequestParam String state,
                                                    @ApiParam(value = "Número de página a consultar") @RequestParam int page,
                                                    @ApiParam(value = "Número de registros a consultar") @RequestParam int rowsNumber){
        return domainService.findDomainPageable(criteria, state, page, rowsNumber);
    }

    @ApiOperation(value = "Realiza el registro de dominio", authorizations = @Authorization(value = "Bearer"))
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public DomainQueryDto saveDomain(@ApiParam(value = "Entidad dominio para el registro") @Valid @RequestBody DomainAddDto domainAddDto){
        return domainService.addDomain(domainAddDto);
    }

    @ApiOperation(value = "Realiza la modificación del dominio", authorizations = @Authorization(value = "Bearer"))
    @PutMapping(path = "{domainId}")
    @ResponseStatus(HttpStatus.OK)
    public DomainQueryDto updateDomain(@ApiParam(value = "Identificador de la entidad") @PathVariable("domainId") long domainId,
                                       @ApiParam(value = "Entidad dominio para la modificación") @Valid @RequestBody DomainUpdateDto domainUpdateDto){
        return domainService.updateDomain(domainId,domainUpdateDto);
    }

    @ApiOperation(value = "Realiza la eliminacion del dominio", authorizations = @Authorization(value = "Bearer"))
    @PutMapping(path = "delete/{domainId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDomain(@ApiParam(value = "Identificador de la entidad") @PathVariable("domainId") long domainId){
        domainService.deleteDomain(domainId);
    }
}
