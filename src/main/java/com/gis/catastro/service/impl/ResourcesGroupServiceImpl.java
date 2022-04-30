package com.gis.catastro.service.impl;

import com.gis.catastro.exception.Message;
import com.gis.catastro.exception.MessageDescription;
import com.gis.catastro.model.entity.QResourcesGroup;
import com.gis.catastro.model.entity.ResourcesGroup;
import com.gis.catastro.model.entity.Route;
import com.gis.catastro.model.repository.ResourcesGroupRepository;
import com.gis.catastro.model.repository.RouteRepository;
import com.gis.catastro.service.ResourcesGroupService;
import com.gis.catastro.service.dto.resourcesGroup.ResourcesGroupAddDto;
import com.gis.catastro.service.dto.resourcesGroup.ResourcesGroupQueryDto;
import com.gis.catastro.service.dto.resourcesGroup.ResourcesGroupQueryPageableDto;
import com.gis.catastro.service.dto.resourcesGroup.ResourcesGroupUpdateDto;
import com.gis.catastro.util.Constants;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ResourcesGroupServiceImpl implements ResourcesGroupService {
    @Autowired
    private ResourcesGroupRepository resourcesGroupRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Override
    public ResourcesGroupQueryPageableDto getResourceGroupPageable(String pState, int pPage, int pRowsNumber) {
        ResourcesGroupQueryPageableDto vResourcesGroupQueryPageableDto = new ResourcesGroupQueryPageableDto();
        Long vTotalRows = resourcesGroupRepository.getCountResourcesGroupByState(pState);
        if(Strings.isNullOrEmpty(pState)){
            throw Message.GetBadRequest(MessageDescription.stateNullOrEmpty);
        }
        if(!(pState.equals(Constants.STATE_ACTIVE) || pState.equals(Constants.STATE_INACTIVE) || pState.equals(Constants.STATE_BLOCKED))){
            Object[] obj = {pState};
            throw Message.GetBadRequest(MessageDescription.stateNotValid, obj);
        }

        if(vTotalRows>0){
            List<ResourcesGroup> vResourcesGroupList = resourcesGroupRepository.getResourcesGroupPageableByState(pState, PageRequest.of(pPage,pRowsNumber));
            List<ResourcesGroupQueryDto> vResourcesGroupQueryDtoList = new ArrayList<>();
            for(ResourcesGroup vResourcesGroup : vResourcesGroupList){
                ResourcesGroupQueryDto vResourcesGroupQueryDto = new ResourcesGroupQueryDto();
                BeanUtils.copyProperties(vResourcesGroup, vResourcesGroupQueryDto);
                if(vResourcesGroup.getRoute() != null){
                    vResourcesGroupQueryDto.setIdRoute(vResourcesGroup.getRoute().getId());
                }
                vResourcesGroupQueryDtoList.add(vResourcesGroupQueryDto);

            }
            vResourcesGroupQueryPageableDto.setTotalRows(vTotalRows);
            vResourcesGroupQueryPageableDto.setResourcesGroupQueryDtoList(vResourcesGroupQueryDtoList);
        }else{
            vResourcesGroupQueryPageableDto.setTotalRows(0);
        }
        return vResourcesGroupQueryPageableDto;
    }

    @Override
    public ResourcesGroupQueryPageableDto findResourceGroupPageable(String pCriteria, String pState, int pPage, int pRowsNumber) {
        ResourcesGroupQueryPageableDto vResourcesGroupQueryPageableDto = new ResourcesGroupQueryPageableDto();
        if(Strings.isNullOrEmpty(pState)){
            throw Message.GetBadRequest(MessageDescription.stateNullOrEmpty);
        }
        if(!(pState.equals(Constants.STATE_ACTIVE) || pState.equals(Constants.STATE_INACTIVE) || pState.equals(Constants.STATE_BLOCKED) || pState.equals(Constants.STATE_DELETED))){
            Object[] obj = {pState};
            throw Message.GetBadRequest(MessageDescription.stateNotValid, obj);
        }

        QResourcesGroup qResourcesGroup = QResourcesGroup.resourcesGroup;
        BooleanExpression vTitle = qResourcesGroup.title.contains(pCriteria);
        BooleanExpression vDescription = qResourcesGroup.description.contains(pCriteria);
        BooleanExpression vState = qResourcesGroup.state.eq(pState);
        Predicate vPredicate = vTitle.or(vDescription).and(vState);
        Long vTotalRows = resourcesGroupRepository.count(vPredicate);
        if(vTotalRows>0){
            List<ResourcesGroup> vResourcesGroupList = Lists.newArrayList(resourcesGroupRepository.findAll(vPredicate, PageRequest.of(pPage,pRowsNumber)));
            List<ResourcesGroupQueryDto> vResourcesGroupQueryDtoList = new ArrayList<>();
            for(ResourcesGroup vResourcesGroup : vResourcesGroupList){
                ResourcesGroupQueryDto vResourcesGroupQueryDto = new ResourcesGroupQueryDto();
                BeanUtils.copyProperties(vResourcesGroup, vResourcesGroupQueryDto);
                if(vResourcesGroup.getRoute() != null){
                    vResourcesGroupQueryDto.setIdRoute(vResourcesGroup.getRoute().getId());
                }
                vResourcesGroupQueryDtoList.add(vResourcesGroupQueryDto);

            }
            vResourcesGroupQueryPageableDto.setTotalRows(vTotalRows);
            vResourcesGroupQueryPageableDto.setResourcesGroupQueryDtoList(vResourcesGroupQueryDtoList);
        }else{
            vResourcesGroupQueryPageableDto.setTotalRows(0);
        }
        return vResourcesGroupQueryPageableDto;
    }

    @Override
    public ResourcesGroupQueryDto addResourcesGroup(ResourcesGroupAddDto pResourcesGroupAddDto) {
        ResourcesGroup vResourcesGroup = new ResourcesGroup();
        BeanUtils.copyProperties(pResourcesGroupAddDto, vResourcesGroup);
        if(pResourcesGroupAddDto.getIdRoute()!=null){
            Route vRoute = routeRepository.getRouteById(pResourcesGroupAddDto.getIdRoute()).orElse(null);
            vResourcesGroup.setRoute(vRoute);
        }
        resourcesGroupRepository.save(vResourcesGroup);
        ResourcesGroupQueryDto vResourcesGroupQueryDto = new ResourcesGroupQueryDto();
        BeanUtils.copyProperties(vResourcesGroup, vResourcesGroupQueryDto);
        return vResourcesGroupQueryDto;
    }

    @Override
    public ResourcesGroupQueryDto updateResourcesGroup(long pResourcesGroupId, ResourcesGroupUpdateDto pResourcesGroupUpdateDto) {
        ResourcesGroup vResourcesGroup = this.getResourcesGroupById(pResourcesGroupId);
        BeanUtils.copyProperties(pResourcesGroupUpdateDto, vResourcesGroup);
        if(pResourcesGroupUpdateDto.getIdRoute()!=null && pResourcesGroupUpdateDto.getIdRoute()==pResourcesGroupId){
            Route vRoute = routeRepository.getRouteById(pResourcesGroupUpdateDto.getIdRoute()).orElse(null);
            vResourcesGroup.setRoute(vRoute);
        }
        resourcesGroupRepository.save(vResourcesGroup);
        ResourcesGroupQueryDto vResourcesGroupQueryDto = new ResourcesGroupQueryDto();
        BeanUtils.copyProperties(vResourcesGroup, vResourcesGroupQueryDto);
        return vResourcesGroupQueryDto;
    }

    @Override
    public void deleteResourcesGroup(long pResourcesGroupId) {
        ResourcesGroup vResourcesGroup = this.getResourcesGroupById(pResourcesGroupId);
        vResourcesGroup.setDeletedDate(new Date());
        vResourcesGroup.setState(Constants.STATE_DELETED);
        resourcesGroupRepository.save(vResourcesGroup);
    }

    @Override
    public ResourcesGroup getResourcesGroupById(long pResourcesGroupId) {
        ResourcesGroup vResourcesGroup = resourcesGroupRepository.getResourcesGroupById(pResourcesGroupId).orElse(null);
        if(vResourcesGroup == null){
            Object[] obj = {"grupo-recurso", "id", String.valueOf(pResourcesGroupId)};
            throw Message.GetBadRequest(MessageDescription.notExists, obj);
        }
        return vResourcesGroup;
    }
}
