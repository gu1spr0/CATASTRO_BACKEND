package com.gis.catastro.service;

import com.gis.catastro.model.entity.Role;
import com.gis.catastro.service.dto.role.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    RoleQueryPageableDto getRoles(String pState, int pPage, int pRowsNumber);
    RoleQueryPageableDto findRoles(String pCriteria, String pState, int pPage, int pRowsNumber);
    RoleQueryDto addRole(RoleAddDto pRoleAddDto);
    RoleQueryDto updateRole(long pRoleId, RoleUpdateDto pRoleUpdateDto);
    void deleteRole(long pRoleId);
    Role getRoleById(long pRoleId);
    List<RoleResourceGroupQueryDto> getRoleResourceGroupsByRoleId(long pRoleId);
    void addRoleResourceGroupsByRoleId(long pRoleId, Long[] pResourceIds);
    List<RolePermissionGroupQueryDto> getRolePermissionsGroupsByRoleId(long pRoleId);
    void addRolePermissionGroupsByRoleId(long pRoleId, Long[] pPermissionIds);
}
