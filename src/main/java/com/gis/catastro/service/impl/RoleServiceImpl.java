package com.gis.catastro.service.impl;

import com.gis.catastro.exception.Message;
import com.gis.catastro.exception.MessageDescription;
import com.gis.catastro.model.entity.*;
import com.gis.catastro.model.repository.*;
import com.gis.catastro.service.RoleService;
import com.gis.catastro.service.dto.permissionsGroup.PermissionGroupQuerySelectDto;
import com.gis.catastro.service.dto.resourcesGroup.ResourcesGroupQuerySelectDto;
import com.gis.catastro.service.dto.role.*;
import com.gis.catastro.util.Constants;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ResourceLoginRepository resourceLoginRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private PermissionLoginRepository permissionLoginRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public RoleQueryPageableDto getRoles(String pState, int pPage, int pRowsNumber) {
        RoleQueryPageableDto vRoleQueryPageableDto = new RoleQueryPageableDto();
        if(Strings.isNullOrEmpty(pState)){
            throw Message.GetBadRequest(MessageDescription.stateNullOrEmpty);
        }
        if(!(pState.equals(Constants.STATE_ACTIVE) || pState.equals(Constants.STATE_INACTIVE) || pState.equals(Constants.STATE_BLOCKED) || pState.equals(Constants.STATE_DELETED))){
            Object[] obj = {pState};
            throw Message.GetBadRequest(MessageDescription.stateNotValid, obj);
        }
        long vTotalRows = roleRepository.getCountRolesByState(pState);
        if (vTotalRows>0){
            List<Role> vRoleList = roleRepository.getRolesPageableByState(pState, PageRequest.of(pPage, pRowsNumber));
            List<RoleQueryDto> vRoleQueryDtoList = new ArrayList<>();
            for (Role vRole: vRoleList){
                RoleQueryDto vRoleQueryDto = new RoleQueryDto();
                BeanUtils.copyProperties(vRole, vRoleQueryDto);
                vRoleQueryDtoList.add(vRoleQueryDto);
            }
            vRoleQueryPageableDto.setRoleQueryDtoList(vRoleQueryDtoList);
            vRoleQueryPageableDto.setTotalRows(vTotalRows);
        }else{
            vRoleQueryPageableDto.setTotalRows(0);
        }
        return vRoleQueryPageableDto;
    }

    @Override
    public RoleQueryPageableDto findRoles(String pCriteria, String pState, int pPage, int pRowsNumber) {
        RoleQueryPageableDto vRoleQueryPageableDto = new RoleQueryPageableDto();
        if(Strings.isNullOrEmpty(pState)){
            throw Message.GetBadRequest(MessageDescription.stateNullOrEmpty);
        }
        if(!(pState.equals(Constants.STATE_ACTIVE) || pState.equals(Constants.STATE_INACTIVE) || pState.equals(Constants.STATE_BLOCKED))){
            Object[] obj = {pState};
            throw Message.GetBadRequest(MessageDescription.stateNotValid, obj);
        }
        QRole qRole = QRole.role1;
        BooleanExpression vRoleName = qRole.role.contains(pCriteria);
        BooleanExpression vDescription = qRole.description.contains(pCriteria);
        BooleanExpression vState = qRole.state.eq(pState);
        Predicate vPredicate = vRoleName.or(vDescription).and(vState);
        long vTotalRows = roleRepository.count(vPredicate);
        if (vTotalRows>0){
            List<Role> vRoleList = Lists.newArrayList(roleRepository.findAll(vPredicate, PageRequest.of(pPage, pRowsNumber)));
            List<RoleQueryDto> vRoleQueryDtoList = new ArrayList<>();
            for (Role vRole: vRoleList){
                RoleQueryDto vRoleQueryDto = new RoleQueryDto();
                BeanUtils.copyProperties(vRole, vRoleQueryDto);
                vRoleQueryDtoList.add(vRoleQueryDto);
            }
            vRoleQueryPageableDto.setRoleQueryDtoList(vRoleQueryDtoList);
            vRoleQueryPageableDto.setTotalRows(vTotalRows);
        }else{
            vRoleQueryPageableDto.setTotalRows(0);
        }
        return vRoleQueryPageableDto;
    }

    @Override
    public RoleQueryDto addRole(RoleAddDto pRoleAddDto) {
        Role vRole = roleRepository.getRoleByRoleName(pRoleAddDto.getRole()).orElse(null);
        if (vRole != null){
            Object[] obj = {"rol", pRoleAddDto.getRole()};
            throw Message.GetBadRequest(MessageDescription.repeated, obj);
        }
        vRole = new Role();
        BeanUtils.copyProperties(pRoleAddDto, vRole);
        roleRepository.save(vRole);
        RoleQueryDto vRoleQueryDto = new RoleQueryDto();
        BeanUtils.copyProperties(vRole, vRoleQueryDto);
        return vRoleQueryDto;
    }

    @Override
    public RoleQueryDto updateRole(long pRoleId, RoleUpdateDto pRoleUpdateDto) {
        Role vRole = this.getRoleById(pRoleId);
        if (!vRole.getRole().equals(pRoleUpdateDto.getRole())){
            if (roleRepository.isRoleNameRepeated(pRoleUpdateDto.getRole())){
                Object[] obj = {"nombre de rol", pRoleUpdateDto.getRole()};
                throw Message.GetBadRequest(MessageDescription.repeated, obj);
            }
        }
        BeanUtils.copyProperties(pRoleUpdateDto, vRole);
        roleRepository.save(vRole);
        RoleQueryDto vRoleQueryDto = new RoleQueryDto();
        BeanUtils.copyProperties(vRole, vRoleQueryDto);
        return vRoleQueryDto;
    }

    @Override
    public void deleteRole(long pRoleId) {
        Role vRole = this.getRoleById(pRoleId);
        vRole.setDeletedDate(new Date());
        vRole.setState(Constants.STATE_DELETED);
        roleRepository.save(vRole);
    }

    @Override
    public Role getRoleById(long pRoleId) {
        Role vRole = roleRepository.getRoleById(pRoleId).orElse(null);
        if (vRole == null){
            Object[] obj = {"rol", "id", String.valueOf(pRoleId)};
            throw Message.GetBadRequest(MessageDescription.notExists, obj);
        }
        return vRole;
    }

    @Override
    public List<RoleResourceGroupQueryDto> getRoleResourceGroupsByRoleId(long pRoleId) {
        List<ResourceLogin> vRoleResourceList = resourceLoginRepository.getResources();
        List<Resource> vResourceList = resourceRepository.getResourcesByRoleId(pRoleId);

        if (vRoleResourceList.size()>0){
            List<RoleResourceGroupQueryDto> vRoleResourceGroupQueryDtoList = new ArrayList<>();
            Map<ResourcesGroupQuerySelectDto, List<ResourceLogin>> rev = vRoleResourceList.stream()
                    .collect(Collectors.groupingBy(resourceGroup -> new ResourcesGroupQuerySelectDto(resourceGroup.getResourceGroupTitle(), resourceGroup.getResourceGroupIcon(), resourceGroup.getResourceGroupDeploymentOrder()), Collectors.toList()));

            rev = rev.entrySet().stream().sorted(Comparator.comparing(p -> p.getKey().getDeploymentOrder()))
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

            for (Map.Entry<ResourcesGroupQuerySelectDto, List<ResourceLogin>> entry : rev.entrySet()) {
                RoleResourceGroupQueryDto vRoleResourceGroupQueryDto = new RoleResourceGroupQueryDto();
                vRoleResourceGroupQueryDto.setTitle(entry.getKey().getTitle());
                List<RoleResourceQueryDto> vResourceLoginQueryDtoList = new ArrayList<>();
                List<ResourceLogin> resourceLoginList = entry.getValue().stream().sorted(Comparator.comparing(ResourceLogin::getResourceDeploymentOrder)).collect(toList());

                for (ResourceLogin resourceLogin : resourceLoginList) {
                    RoleResourceQueryDto vRoleResourceQueryDto = new RoleResourceQueryDto();
                    vRoleResourceQueryDto.setTitle(resourceLogin.getResourceTitle());
                    vRoleResourceQueryDto.setId(resourceLogin.getResourceId());
                    Resource vResource = vResourceList.stream().filter(x -> x.getId()==resourceLogin.getResourceId()).
                            findFirst().orElse(null);
                    if (vResource==null){
                        vRoleResourceQueryDto.setAssign(false);
                    }else{
                        vRoleResourceQueryDto.setAssign(true);
                    }
                    vResourceLoginQueryDtoList.add(vRoleResourceQueryDto);
                }
                vRoleResourceGroupQueryDto.setRoleResourceQueryDtoList(vResourceLoginQueryDtoList);
                vRoleResourceGroupQueryDtoList.add(vRoleResourceGroupQueryDto);
            }
            return vRoleResourceGroupQueryDtoList;
        } else {
            throw Message.GetBadRequest(MessageDescription.UserWithoutResources);
        }
    }

    @Override
    public void addRoleResourceGroupsByRoleId(long pRoleId, Long[] pResourceIds) {
        Role vRole = this.getRoleById(pRoleId);
        List<Resource> vResourceList = resourceRepository.getResourcesByIds(pResourceIds);
        vRole.setResources(vResourceList);
        roleRepository.save(vRole);
    }

    @Override
    public List<RolePermissionGroupQueryDto> getRolePermissionsGroupsByRoleId(long pRoleId) {
        List<PermissionLogin> vRolePermissionList = permissionLoginRepository.getPermissions();
        List<Permission> vPermissionList = permissionRepository.getPermissionsByRoleId(pRoleId);

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

    @Override
    public void addRolePermissionGroupsByRoleId(long pRoleId, Long[] pPermissionIds) {
        Role vRole = this.getRoleById(pRoleId);
        List<Permission> vPermissionList = permissionRepository.getPermissionsByIds(pPermissionIds);
        vRole.setPermissions(vPermissionList);
        roleRepository.save(vRole);
    }
}
