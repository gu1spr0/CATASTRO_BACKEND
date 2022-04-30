package com.gis.catastro.controller;

import com.gis.catastro.service.ConnectionLogService;
import com.gis.catastro.service.UserService;
import com.gis.catastro.service.dto.user.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(description = "Endpoints para la gestión de usuarios")
@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;
    private final ConnectionLogService connectionLogService;

    public UserController(UserService userService,
                          ConnectionLogService connectionLogService){
        this.userService = userService;
        this.connectionLogService = connectionLogService;
    }

    @ApiOperation(value = "Obtiene un listado de usuarios paginado por estado", authorizations = @Authorization(value = "Bearer"))
    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public UserQueryPageableDto getUsersPageable(@ApiParam(value = "Estado del usuario a filtrar") @RequestParam String state,
                                                 @ApiParam(value = "Número de página a consultar") @RequestParam int page,
                                                 @ApiParam(value = "Número de registros a consultar") @RequestParam int rowsNumber){
        return userService.getUsersPageable(state, page, rowsNumber);
    }

    @ApiOperation(value = "Obtiene un listado de usuarios filtrado paginado por estado", authorizations = @Authorization(value = "Bearer"))
    @GetMapping(path = "find")
    @ResponseStatus(HttpStatus.OK)
    public UserQueryPageableDto findUsersPageable(@ApiParam(value = "Valor para filtrar") @RequestParam String criteria,
                                                  @ApiParam(value = "Estado del usuario a filtrar") @RequestParam String state,
                                                  @ApiParam(value = "Número de página a consultar") @RequestParam int page,
                                                  @ApiParam(value = "Número de registros a consultar") @RequestParam int rowsNumber){
        return userService.findUsersPageable(criteria, state, page, rowsNumber);
    }

    @ApiOperation(value = "Crea un usuario", authorizations = @Authorization(value = "Bearer"))
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public UserQueryDto addUser(@ApiParam(value = "Datos del usuario") @Valid @RequestBody UserAddDto userAddDto){
        return userService.addUser(userAddDto);
    }

    @ApiOperation(value = "Actualiza la información de un usuario", authorizations = @Authorization(value = "Bearer"))
    @PutMapping(path = "{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserQueryDto updateUser(@ApiParam(value = "Identificador del rol") @PathVariable Long userId,
                                   @ApiParam(value = "Datos a actualizar") @Valid @RequestBody UserUpdateDto userUpdateDto){
        return userService.updateUser(userId, userUpdateDto);
    }

    @ApiOperation(value = "Elimina un usuario", authorizations = @Authorization(value = "Bearer"))
    @PutMapping(path = "delete/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@ApiParam(value = "Identificador del usuario") @PathVariable Long userId){
        userService.deleteUser(userId);
    }

    @ApiOperation(value = "Obtiene los roles asignados a un usuario", authorizations = @Authorization(value = "Bearer"))
    @GetMapping(path = "{userId}/roles")
    @ResponseStatus(HttpStatus.OK)
    public List<UserRoleQueryDto> getUserRolesByUserId(@ApiParam(value = "Identificador del usuario") @PathVariable Long userId) {
        return userService.getUserRolesByUserId(userId);
    }

    @ApiOperation(value = "Asigna roles a un usuario", authorizations = @Authorization(value = "Bearer"))
    @PostMapping(path = "{userId}/roles")
    @ResponseStatus(HttpStatus.CREATED)
    public void addRolesByUserId(@ApiParam(value = "Identificador del usuario") @PathVariable Long userId,
                                 @ApiParam(value = "IDs de los roles") @Valid @RequestBody Long[] roleIDs) {
        userService.addRolesByUserId(userId, roleIDs);
    }

    @ApiOperation(value = "Registra el logout de un usuario", authorizations = @Authorization(value = "Bearer"))
    @PutMapping(path = "logout")
    @ResponseStatus(HttpStatus.OK)
    public void logoutUser(){
        connectionLogService.updateConnectionLogout();
    }
}
