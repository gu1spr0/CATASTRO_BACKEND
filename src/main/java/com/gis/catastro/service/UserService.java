package com.gis.catastro.service;

import com.gis.catastro.model.entity.User;
import com.gis.catastro.service.dto.resourcesGroup.ResourcesGroupLoginQueryDto;
import com.gis.catastro.service.dto.user.*;

import java.util.List;

public interface UserService {
    UserQueryPageableDto getUsersPageable(String pState, int pPage, int pRowsNumber);
    UserQueryPageableDto findUsersPageable(String pCriteria,String pState, int pPage, int pRowsNumber);
    UserQueryDto addUser(UserAddDto pUserAddDto);
    UserQueryDto updateUser(long pUserId, UserUpdateDto pUserUpdateDto);
    void deleteUser(long pUserId);
    User getUserById(long pUserId);
    User getUserByUserName(String pUserName);
    List<ResourcesGroupLoginQueryDto> getMenuByResources(Long[] pResourceIdList);
    List<UserRoleQueryDto> getUserRolesByUserId(long pUserId);
    void addRolesByUserId(long pUserId, Long[] roleIds);
}
