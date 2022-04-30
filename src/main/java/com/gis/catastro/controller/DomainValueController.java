package com.gis.catastro.controller;

import com.gis.catastro.service.DomainValueService;
import com.gis.catastro.service.dto.domainValue.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(description = "Endpoints para la gestión de valores de dominio")
@RestController
@RequestMapping("api/domains/value")
public class DomainValueController {
    private final DomainValueService domainValueService;
    public DomainValueController(DomainValueService domainValueService){
        this.domainValueService = domainValueService;
    }


    @ApiOperation(value = "Obtiene un listado de valores de dominio paginados por estado y por dominio", authorizations = @Authorization(value = "Bearer"))
    @GetMapping(path = "/{domainId}")
    @ResponseStatus(HttpStatus.OK)
    public DomainValueQueryPageableDto getDomainValuePageable(@ApiParam(value="Identificador de dominio") @PathVariable("domainId") long domainId,
                                                              @ApiParam(value = "Estado del valor de dominio a filtrar") @RequestParam String state,
                                                              @ApiParam(value = "Número de página a consultar") @RequestParam int page,
                                                              @ApiParam(value = "Número de registros a consultar") @RequestParam int rowsNumber){
        return domainValueService.getDomainValuePageable(domainId,state, page, rowsNumber);
    }

    @ApiOperation(value = "Obtiene un listado de valores de dominio paginados por estado y por dominio", authorizations = @Authorization(value = "Bearer"))
    @GetMapping(path = "/{domainId}/find")
    @ResponseStatus(HttpStatus.OK)
    public DomainValueQueryPageableDto findDomainValuePageable(@ApiParam(value="Identificador de dominio") @PathVariable("domainId") long domainId,
                                                               @ApiParam(value = "Criterio a filtrar") @RequestParam String criteria,
                                                               @ApiParam(value = "Estado del valor de dominio a filtrar") @RequestParam String state,
                                                               @ApiParam(value = "Número de página a consultar") @RequestParam int page,
                                                               @ApiParam(value = "Número de registros a consultar") @RequestParam int rowsNumber){
        return domainValueService.findDomainValuePageable(domainId, criteria, state, page, rowsNumber);
    }

    @ApiOperation(value = "Realiza el registro del valor de dominio", authorizations = @Authorization(value = "Bearer"))
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public DomainValueQueryDto saveDomainValue(@ApiParam(value = "Entidad dominio para el registro") @Valid @RequestBody DomainValueAddDto domainValueAddDto){
        return domainValueService.addDomainValue(domainValueAddDto);
    }
    @ApiOperation(value = "Realiza la modificación del valor de dominio", authorizations = @Authorization(value = "Bearer"))
    @PutMapping(path = "/{domainValueId}")
    @ResponseStatus(HttpStatus.OK)
    public DomainValueQueryDto updateDomainValue(@ApiParam(value = "Identificador de la entidad") @PathVariable("domainValueId") long domainValueId,
                                                 @ApiParam(value = "Entidad dominio para la modificación") @Valid @RequestBody DomainValueUpdateDto domainValueUpdateDto){
        return domainValueService.updateDomainValue(domainValueId,domainValueUpdateDto);
    }

    @ApiOperation(value = "Realiza la eliminacion del valor de dominio", authorizations = @Authorization(value = "Bearer"))
    @PutMapping(path = "delete/{domainValueId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDomainValue(@ApiParam(value = "Identificador de la entidad") @PathVariable("domainValueId") long domainValueId){
        domainValueService.deleteDomainValue(domainValueId);
    }

    @ApiOperation(value = "Obtiene un listado de valores de dominios para select por código de dominio", authorizations = @Authorization(value = "Bearer"))
    @GetMapping(path = "select")
    @ResponseStatus(HttpStatus.OK)
    public List<DomainValueQuerySelectDto> getDomainValueSelectByDomainCode(@ApiParam(value = "Código del dominio") @RequestParam String domainCode){
        return domainValueService.getDomainValueSelectByDomainCode(domainCode);
    }

    @ApiOperation(value = "Obtiene select de valores relacionados", authorizations = @Authorization(value = "Bearer"))
    @GetMapping(path = "relation")
    @ResponseStatus(HttpStatus.OK)
    public List<DomainValueQuerySelectDto> findRelationByDomainCodeAndCharValue(
            @ApiParam(value = "Identificador del dominio") @RequestParam String domainCode,
            @ApiParam(value = "Identificador del código del valor del dominio padre") @RequestParam(required = false) String charValue,
            @ApiParam(value = "Identificador del código del valor extra del dominio padre") @RequestParam(required = false) String charValueExtra){
        return domainValueService.getRelation(domainCode, charValue, charValueExtra);
    }
}
