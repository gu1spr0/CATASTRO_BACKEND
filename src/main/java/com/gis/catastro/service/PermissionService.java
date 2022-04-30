package com.gis.catastro.service;

import com.gis.catastro.service.dto.role.RolePermissionGroupQueryDto;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface PermissionService {
    List<RolePermissionGroupQueryDto> getRolePermissionsGroupsByRolesId(String[] pRoles);
}
