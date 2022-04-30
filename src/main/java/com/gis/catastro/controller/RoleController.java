package com.gis.catastro.controller;

import com.gis.catastro.service.RoleService;
import com.gis.catastro.service.dto.role.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(description = "Endpoints para la gestión de roles")
@RestController
@RequestMapping("api/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }

    @ApiOperation(value = "Obtiene un listado de todos los roles paginado", authorizations = @Authorization(value = "Bearer"))
    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public RoleQueryPageableDto getRoles(@ApiParam(value = "Estado de los registros") @RequestParam String state,
                                         @ApiParam(value = "Número de página a consultar") @RequestParam int page,
                                         @ApiParam(value = "Número de registros a consultar") @RequestParam int rowsNumber){
        return roleService.getRoles(state, page, rowsNumber);
    }

    @ApiOperation(value = "Obtiene un listado filtrado de todos los roles paginado", authorizations = @Authorization(value = "Bearer"))
    @GetMapping(path = "find")
    @ResponseStatus(HttpStatus.OK)
    public RoleQueryPageableDto findRoles(@ApiParam(value = "Estado de los registros") @RequestParam String criteria,
                                          @ApiParam(value = "Estado de los registros") @RequestParam String state,
                                          @ApiParam(value = "Número de página a consultar") @RequestParam int page,
                                          @ApiParam(value = "Número de registros a consultar") @RequestParam int rowsNumber){
        return roleService.findRoles(criteria, state, page, rowsNumber);
    }

    @ApiOperation(value = "Crea un rol", authorizations = @Authorization(value = "Bearer"))
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public RoleQueryDto addRole(@ApiParam(value = "Datos del rol") @Valid @RequestBody RoleAddDto roleAddDto){
        return roleService.addRole(roleAddDto);
    }

    @ApiOperation(value = "Actualiza la información de un rol", authorizations = @Authorization(value = "Bearer"))
    @PutMapping(path = "{roleId}")
    @ResponseStatus(HttpStatus.OK)
    public RoleQueryDto updateRole(@ApiParam(value = "Identificador del rol") @PathVariable Long roleId,
                                   @ApiParam(value = "Datos a actualizar") @Valid @RequestBody RoleUpdateDto roleUpdateDto){
        return roleService.updateRole(roleId, roleUpdateDto);
    }

    @ApiOperation(value = "Elimina un rol", authorizations = @Authorization(value = "Bearer"))
    @PutMapping(path = "delete/{roleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@ApiParam(value = "Identificador del rol") @PathVariable Long roleId){
        roleService.deleteRole(roleId);
    }

    @ApiOperation(value = "Obtiene los recursos asignados a un rol", authorizations = @Authorization(value = "Bearer"))
    @GetMapping(path = "{roleId}/resources")
    @ResponseStatus(HttpStatus.OK)
    public List<RoleResourceGroupQueryDto> getRoleResourceGroupsByRoleId(@ApiParam(value = "Identificador del rol") @PathVariable Long roleId) {
        return roleService.getRoleResourceGroupsByRoleId(roleId);
    }

    @ApiOperation(value = "Asigna recursos a un rol", authorizations = @Authorization(value = "Bearer"))
    @PostMapping(path = "{roleId}/resources")
    @ResponseStatus(HttpStatus.CREATED)
    public void addRoleResourceGroupsByRoleId(@ApiParam(value = "Identificador del rol") @PathVariable Long roleId,
                                              @ApiParam(value = "IDs de los recursos") @Valid @RequestBody Long[] resourcesIDs) {
        roleService.addRoleResourceGroupsByRoleId(roleId, resourcesIDs);
    }

    @ApiOperation(value = "Obtiene los permisos asignados a un rol", authorizations = @Authorization(value = "Bearer"))
    @GetMapping(path = "{roleId}/permissions")
    @ResponseStatus(HttpStatus.OK)
    public List<RolePermissionGroupQueryDto> getRolePermissionsGroupsByRoleId(@ApiParam(value = "Identificador del rol") @PathVariable Long roleId) {
        return roleService.getRolePermissionsGroupsByRoleId(roleId);
    }

    @ApiOperation(value = "Asigna permisos a un rol", authorizations = @Authorization(value = "Bearer"))
    @PostMapping(path = "{roleId}/permissions")
    @ResponseStatus(HttpStatus.CREATED)
    public void addRolePermissionGroupsByRoleId(@ApiParam(value = "Identificador del rol") @PathVariable Long roleId,
                                                @ApiParam(value = "IDs de los permisos") @Valid @RequestBody Long[] permissionsIDs) {
        roleService.addRolePermissionGroupsByRoleId(roleId, permissionsIDs);
    }
}
