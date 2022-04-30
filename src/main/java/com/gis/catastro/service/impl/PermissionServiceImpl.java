package com.gis.catastro.service.impl;

import com.gis.catastro.exception.Message;
import com.gis.catastro.exception.MessageDescription;
import com.gis.catastro.model.entity.Permission;
import com.gis.catastro.model.entity.PermissionLogin;
import com.gis.catastro.model.repository.PermissionLoginRepository;
import com.gis.catastro.model.repository.PermissionRepository;
import com.gis.catastro.service.PermissionService;
import com.gis.catastro.service.dto.permissionsGroup.PermissionGroupQuerySelectDto;
import com.gis.catastro.service.dto.role.RolePermissionGroupQueryDto;
import com.gis.catastro.service.dto.role.RolePermissionQueryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionLoginRepository permissionLoginRepository;


    @Override
    public List<RolePermissionGroupQueryDto> getRolePermissionsGroupsByRolesId(String[] pRoles) {
        List<PermissionLogin> vRolePermissionList = permissionLoginRepository.getPermissions();
        List<Permission> vPermissionList = permissionRepository.getPermissionsByRoleIds(pRoles);

        if (vRolePermissionList.size()>0){
            List<RolePermissionGroupQueryDto> vRolePermissionGroupQueryDtoList = new ArrayList<>();
            Map<PermissionGroupQuerySelectDto, List<PermissionLogin>> rev = vRolePermissionList.stream()
                    .collect(Collectors.groupingBy(permissionGroup -> new PermissionGroupQuerySelectDto(permissionGroup.getPermissionGroupTitle(), permissionGroup.getPermissionGroupCode(), permissionGroup.getPermissionGroupDeploymentOrder()), Collectors.toList()));

            rev = rev.entrySet().stream().sorted(Comparator.comparing(p -> p.getKey().getDeploymentOrder()))
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

            for (Map.Entry<PermissionGroupQuerySelectDto, List<PermissionLogin>> entry : rev.entrySet()) {
                RolePermissionGroupQueryDto vRolePermissionGroupQueryDto = new RolePermissionGroupQueryDto();
                vRolePermissionGroupQueryDto.setTitle(entry.getKey().getTitle());
                vRolePermissionGroupQueryDto.setPermissionGroupCode(entry.getKey().getPermissionGroupCode());
                List<RolePermissionQueryDto> vPermissionLoginQueryDtoList = new ArrayList<>();
                List<PermissionLogin> PermissionLoginList = entry.getValue().stream().sorted(Comparator.comparing(PermissionLogin::getPermissionDeploymentOrder)).collect(toList());

                for (PermissionLogin permissionLogin : PermissionLoginList) {
                    RolePermissionQueryDto vRolePermissionQueryDto = new RolePermissionQueryDto();
                    vRolePermissionQueryDto.setTitle(permissionLogin.getPermissionTitle());
                    vRolePermissionQueryDto.setPermissionCode(permissionLogin.getPermissionCode());
                    vRolePermissionQueryDto.setId(permissionLogin.getPermissionId());
                    Permission vPermission = vPermissionList.stream().filter(x -> x.getId()==permissionLogin.getPermissionId()).
                            findFirst().orElse(null);
                    if (vPermission==null){
                        vRolePermissionQueryDto.setAssign(false);
                    }else{
                        vRolePermissionQueryDto.setAssign(true);
                    }
                    vPermissionLoginQueryDtoList.add(vRolePermissionQueryDto);
                }
                vRolePermissionGroupQueryDto.setRolePermissionQueryDtoList(vPermissionLoginQueryDtoList);
                vRolePermissionGroupQueryDtoList.add(vRolePermissionGroupQueryDto);
            }
            return vRolePermissionGroupQueryDtoList;
        } else {
            throw Message.GetBadRequest(MessageDescription.UserWithoutPermissions);
        }
    }
}
