package com.gis.catastro.service.impl;

import com.gis.catastro.exception.Message;
import com.gis.catastro.exception.MessageDescription;
import com.gis.catastro.model.entity.*;
import com.gis.catastro.model.repository.ResourceLoginRepository;
import com.gis.catastro.model.repository.RoleRepository;
import com.gis.catastro.model.repository.UserRepository;
import com.gis.catastro.service.DomainValueService;
import com.gis.catastro.service.UserService;
import com.gis.catastro.service.dto.resourceLogin.ResourceLoginQueryDto;
import com.gis.catastro.service.dto.resourcesGroup.ResourcesGroupLoginQueryDto;
import com.gis.catastro.service.dto.resourcesGroup.ResourcesGroupQuerySelectDto;
import com.gis.catastro.service.dto.user.*;
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
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResourceLoginRepository resourceLoginRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DomainValueService domainValueService;


    @Override
    public UserQueryPageableDto getUsersPageable(String pState, int pPage, int pRowsNumber) {
        UserQueryPageableDto vUserQueryPageableDto = new UserQueryPageableDto();
        if(Strings.isNullOrEmpty(pState)){
            throw Message.GetBadRequest(MessageDescription.stateNullOrEmpty);
        }
        if(!(pState.equals(Constants.STATE_ACTIVE) || pState.equals(Constants.STATE_INACTIVE) || pState.equals(Constants.STATE_BLOCKED) || pState.equals(Constants.STATE_DELETED))){
            Object[] obj = {pState};
            throw Message.GetBadRequest(MessageDescription.stateNotValid, obj);
        }
        long vTotalRows = userRepository.getCountUsersByState(pState);
        if (vTotalRows>0){
            List<User> vUserList = userRepository.getUsersPageableByState(pState, PageRequest.of(pPage, pRowsNumber));
            List<UserQueryDto> vUserQueryDtoList = new ArrayList<>();
            for (User vUser: vUserList){
                UserQueryDto vUserQueryDto = new UserQueryDto();
                BeanUtils.copyProperties(vUser, vUserQueryDto);
                vUserQueryDtoList.add(vUserQueryDto);
            }
            vUserQueryPageableDto.setTotalRows(vTotalRows);
            vUserQueryPageableDto.setUserQueryDtoList(vUserQueryDtoList);
        }else{
            vUserQueryPageableDto.setTotalRows(0);
        }
        return vUserQueryPageableDto;
    }

    @Override
    public UserQueryPageableDto findUsersPageable(String pCriteria, String pState, int pPage, int pRowsNumber) {
        UserQueryPageableDto vUserQueryPageableDto = new UserQueryPageableDto();
        if(Strings.isNullOrEmpty(pState)){
            throw Message.GetBadRequest(MessageDescription.stateNullOrEmpty);
        }
        if(!(pState.equals(Constants.STATE_ACTIVE) || pState.equals(Constants.STATE_INACTIVE) || pState.equals(Constants.STATE_BLOCKED))){
            Object[] obj = {pState};
            throw Message.GetBadRequest(MessageDescription.stateNotValid, obj);
        }
        QUser qUser = QUser.user;
        BooleanExpression vName = qUser.name.contains(pCriteria);
        BooleanExpression vLastName = qUser.lastName.contains(pCriteria);
        BooleanExpression vEmail = qUser.email.contains(pCriteria);
        BooleanExpression vUsername = qUser.username.contains(pCriteria);
        BooleanExpression vTelephone = qUser.telephone.contains(pCriteria);
        BooleanExpression vState = qUser.state.eq(pState);
        Predicate predicate = vName.or(vLastName).or(vEmail).or(vUsername).or(vTelephone).and(vState);
        long vTotalRows = userRepository.count(predicate);
        if (vTotalRows>0){
            List<User> vUserList = Lists.newArrayList(userRepository.findAll(predicate, PageRequest.of(pPage, pRowsNumber)));
            List<UserQueryDto> vUserQueryDtoList = new ArrayList<>();
            for (User vUser: vUserList){
                UserQueryDto vUserQueryDto = new UserQueryDto();
                BeanUtils.copyProperties(vUser, vUserQueryDto);
                vUserQueryDtoList.add(vUserQueryDto);
            }
            vUserQueryPageableDto.setTotalRows(vTotalRows);
            vUserQueryPageableDto.setUserQueryDtoList(vUserQueryDtoList);
        }else{
            vUserQueryPageableDto.setTotalRows(0);
        }
        return vUserQueryPageableDto;
    }

    @Override
    public UserQueryDto addUser(UserAddDto pUserAddDto) {
        User vUser = userRepository.getUserByUsername(pUserAddDto.getUsername()).orElse(null);
        if (vUser != null){
            Object[] obj = {"nombre de usuario", pUserAddDto.getUsername()};
            throw Message.GetBadRequest(MessageDescription.repeated, obj);
        }
        vUser = new User();
        BeanUtils.copyProperties(pUserAddDto, vUser);
        // TODO: delete setpassword
        vUser.setPassword("$2a$10$DOMDxjYyfZ/e7RcBfUpzqeaCs8pLgcizuiQWXPkU35nOhZlFcE9MS");
        userRepository.save(vUser);
        UserQueryDto vUserQueryDto = new UserQueryDto();
        BeanUtils.copyProperties(vUser, vUserQueryDto);
        return vUserQueryDto;
    }

    @Override
    public UserQueryDto updateUser(long pUserId, UserUpdateDto pUserUpdateDto) {
        User vUser = this.getUserById(pUserId);
        if (!vUser.getUsername().equals(pUserUpdateDto.getUsername())){
            if (userRepository.isUserNameRepeated(pUserUpdateDto.getUsername())){
                Object[] obj = {"nombre de usuario", pUserUpdateDto.getUsername()};
                throw Message.GetBadRequest(MessageDescription.repeated, obj);
            }
        }
        BeanUtils.copyProperties(pUserUpdateDto, vUser);
        userRepository.save(vUser);
        UserQueryDto vUserQueryDto = new UserQueryDto();
        BeanUtils.copyProperties(vUser, vUserQueryDto);
        return vUserQueryDto;
    }

    @Override
    public void deleteUser(long pUserId) {
        User vUser = this.getUserById(pUserId);
        vUser.setDeletedDate(new Date());
        vUser.setState(Constants.STATE_DELETED);
        userRepository.save(vUser);
    }

    @Override
    public User getUserById(long pUserId) {
        User vUser = userRepository.getUserById(pUserId).orElse(null);
        if (vUser == null){
            Object[] obj = {"usuario", "id", String.valueOf(pUserId)};
            throw Message.GetBadRequest(MessageDescription.notExists, obj);
        }
        return vUser;
    }

    @Override
    public User getUserByUserName(String pUserName) {
        User user = userRepository.getUserByUsername(pUserName).orElse(null);
        if (user == null){
            Object[] obj = {"usuario", "nombre de usuario", String.valueOf(pUserName)};
            throw Message.GetBadRequest(MessageDescription.notExists, obj);
        }
        return user;
    }

    @Override
    public List<ResourcesGroupLoginQueryDto> getMenuByResources(Long[] pResourceIdList) {
        List<ResourceLogin> vResourceLoginList = resourceLoginRepository.getResourcesById(pResourceIdList);
        if (vResourceLoginList.size()>0){
            List<ResourcesGroupLoginQueryDto> resourceGroupLoginQueryDtoList = new ArrayList<>();
            Map<ResourcesGroupQuerySelectDto, List<ResourceLogin>> rev = vResourceLoginList.stream()
                    .collect(Collectors.groupingBy(resourceGroup -> new ResourcesGroupQuerySelectDto(resourceGroup.getResourceGroupTitle(), resourceGroup.getResourceGroupIcon(), resourceGroup.getResourceGroupDeploymentOrder()), Collectors.toList()));

            rev = rev.entrySet().stream().sorted(Comparator.comparing(p -> p.getKey().getDeploymentOrder()))
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

            for (Map.Entry<ResourcesGroupQuerySelectDto, List<ResourceLogin>> entry : rev.entrySet()) {
                ResourcesGroupLoginQueryDto resourceGroupLoginQueryDto = new ResourcesGroupLoginQueryDto();
                resourceGroupLoginQueryDto.setTitle(entry.getKey().getTitle());
                resourceGroupLoginQueryDto.setIcon(entry.getKey().getIcon());
                resourceGroupLoginQueryDto.setDeploymentOrder(entry.getKey().getDeploymentOrder());
                List<ResourceLoginQueryDto> resourceLoginQueryDtoList = new ArrayList<>();
                List<ResourceLogin> resourceLoginList = entry.getValue().stream().sorted(Comparator.comparing(ResourceLogin::getResourceDeploymentOrder)).collect(toList());

                for (ResourceLogin resourceLogin : resourceLoginList) {
                    ResourceLoginQueryDto resourceLoginQueryDto = new ResourceLoginQueryDto();
                    resourceLoginQueryDto.setTitle(resourceLogin.getResourceTitle());
                    resourceLoginQueryDto.setRoute(resourceLogin.getRoute());
                    resourceLoginQueryDtoList.add(resourceLoginQueryDto);
                }
                resourceGroupLoginQueryDto.setResourceLoginQueryDtoList(resourceLoginQueryDtoList);
                resourceGroupLoginQueryDtoList.add(resourceGroupLoginQueryDto);
            }
            return resourceGroupLoginQueryDtoList;
        } else {
            throw Message.GetBadRequest(MessageDescription.UserWithoutResources);
        }
    }

    @Override
    public List<UserRoleQueryDto> getUserRolesByUserId(long pUserId) {
        List<Role> vRoleList = roleRepository.getRolesByState(Constants.STATE_ACTIVE);
        List<Role> vUserRoleList = roleRepository.getUserRolesByUsername(pUserId);
        List<UserRoleQueryDto> vUserRoleQueryDtoList = new ArrayList<>();
        for (Role vRole: vRoleList){
            UserRoleQueryDto vUserRoleQueryDto = new UserRoleQueryDto();
            vUserRoleQueryDto.setId(vRole.getId());
            vUserRoleQueryDto.setRole(vRole.getRole());
            vUserRoleQueryDto.setDescription(vRole.getDescription());
            Role vUserRole = vUserRoleList.stream().filter(x -> x.getId()==vRole.getId()).
                    findFirst().orElse(null);
            if (vUserRole == null){
                vUserRoleQueryDto.setAssigned(false);
            }else {
                vUserRoleQueryDto.setAssigned(true);
            }
            vUserRoleQueryDtoList.add(vUserRoleQueryDto);
        }
        return vUserRoleQueryDtoList;
    }

    @Override
    public void addRolesByUserId(long pUserId, Long[] roleIds) {
        User vUser = this.getUserById(pUserId);
        List<Role> vRoleList = roleRepository.getRolesByIds(roleIds);
        vUser.setRoles(vRoleList);
        userRepository.save(vUser);
    }
}
