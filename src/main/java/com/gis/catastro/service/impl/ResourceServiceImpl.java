package com.gis.catastro.service.impl;

import com.gis.catastro.exception.Message;
import com.gis.catastro.exception.MessageDescription;
import com.gis.catastro.model.entity.QResource;
import com.gis.catastro.model.entity.Resource;
import com.gis.catastro.model.entity.ResourcesGroup;
import com.gis.catastro.model.entity.Route;
import com.gis.catastro.model.repository.ResourceRepository;
import com.gis.catastro.model.repository.ResourcesGroupRepository;
import com.gis.catastro.model.repository.RouteRepository;
import com.gis.catastro.service.ResourceService;
import com.gis.catastro.service.dto.resource.ResourceAddDto;
import com.gis.catastro.service.dto.resource.ResourceQueryDto;
import com.gis.catastro.service.dto.resource.ResourceQueryPageableDto;
import com.gis.catastro.service.dto.resource.ResourceUpdateDto;
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
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ResourcesGroupRepository resourcesGroupRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Override
    public ResourceQueryPageableDto getResourcePageable(Long pResourceGroupId, String pState, int pPage, int pRowsNumber) {
        ResourceQueryPageableDto vResourceQueryPageableDto = new ResourceQueryPageableDto();
        Long vTotalRows = resourceRepository.getCountResourcesValuesByIdAndState(pResourceGroupId, pState);
        if(Strings.isNullOrEmpty(pState)){
            throw Message.GetBadRequest(MessageDescription.stateNullOrEmpty);
        }
        if(!(pState.equals(Constants.STATE_ACTIVE) || pState.equals(Constants.STATE_INACTIVE) || pState.equals(Constants.STATE_BLOCKED))){
            Object[] obj = {pState};
            throw Message.GetBadRequest(MessageDescription.stateNotValid, obj);
        }

        if(vTotalRows>0){
            List<Resource> vResourceList = resourceRepository.getResourcesPageableByIdAndState(pResourceGroupId, pState, PageRequest.of(pPage,pRowsNumber));
            List<ResourceQueryDto> vResourceQueryDtoList = new ArrayList<>();
            for(Resource vResource: vResourceList){
                ResourceQueryDto vResourceQueryDto = new ResourceQueryDto();
                BeanUtils.copyProperties(vResource, vResourceQueryDto);
                vResourceQueryDto.setIdResourcesGroup(vResource.getResourcesGroup().getId());
                vResourceQueryDto.setIdRoute(vResource.getRoute().getId());
                vResourceQueryDtoList.add(vResourceQueryDto);
            }
            vResourceQueryPageableDto.setTotalRows(vTotalRows);
            vResourceQueryPageableDto.setResourceQueryDtoList(vResourceQueryDtoList);
        }else{
            vResourceQueryPageableDto.setTotalRows(0);
        }
        return vResourceQueryPageableDto;
    }

    @Override
    public ResourceQueryPageableDto findResourcePageable(Long pResourceGroupId, String pCriteria, String pState, int pPage, int pRowsNumber) {
        ResourceQueryPageableDto vResourceQueryPageableDto = new ResourceQueryPageableDto();
        if(Strings.isNullOrEmpty(pState)){
            throw Message.GetBadRequest(MessageDescription.stateNullOrEmpty);
        }
        if(!(pState.equals(Constants.STATE_ACTIVE) || pState.equals(Constants.STATE_INACTIVE) || pState.equals(Constants.STATE_BLOCKED) || pState.equals(Constants.STATE_DELETED))){
            Object[] obj = {pState};
            throw Message.GetBadRequest(MessageDescription.stateNotValid, obj);
        }
        QResource qResource = QResource.resource;
        BooleanExpression vTitle = qResource.title.contains(pCriteria);
        BooleanExpression vDescription = qResource.description.contains(pCriteria);
        BooleanExpression vResourceGroup = qResource.resourcesGroup.id.eq(pResourceGroupId);
        BooleanExpression vState = qResource.state.eq(pState);
        Predicate vPredicate = vTitle.or(vDescription).and(vResourceGroup).and(vState);
        long vTotalRows = resourceRepository.count(vPredicate);
        if(vTotalRows>0){
            List<Resource> vResourceList = Lists.newArrayList(resourceRepository.findAll(vPredicate, PageRequest.of(pPage,pRowsNumber)));
            List<ResourceQueryDto> vResourceQueryDtoList = new ArrayList<>();
            for(Resource vResource: vResourceList){
                ResourceQueryDto vResourceQueryDto = new ResourceQueryDto();
                BeanUtils.copyProperties(vResource, vResourceQueryDto);
                vResourceQueryDto.setIdResourcesGroup(vResource.getResourcesGroup().getId());
                vResourceQueryDto.setIdRoute(vResource.getRoute().getId());
                vResourceQueryDtoList.add(vResourceQueryDto);
            }
            vResourceQueryPageableDto.setTotalRows(vTotalRows);
            vResourceQueryPageableDto.setResourceQueryDtoList(vResourceQueryDtoList);
        }else{
            vResourceQueryPageableDto.setTotalRows(0);
        }
        return vResourceQueryPageableDto;
    }

    @Override
    public ResourceQueryDto addResource(ResourceAddDto pResourceAddDto) {
        Resource vResource = new Resource();
        ResourcesGroup vResourcesGroup = resourcesGroupRepository.getResourcesGroupById(pResourceAddDto.getIdResourcesGroup()).orElse(null);//I
        Route vRoute = routeRepository.getRouteById(pResourceAddDto.getIdRoute()).orElse(null);//I
        BeanUtils.copyProperties(pResourceAddDto, vResource);
        vResource.setResourcesGroup(vResourcesGroup);//I
        vResource.setRoute(vRoute);
        resourceRepository.save(vResource);
        ResourceQueryDto vResourceQueryDto = new ResourceQueryDto();
        BeanUtils.copyProperties(vResource, vResourceQueryDto);
        return vResourceQueryDto;
    }

    @Override
    public ResourceQueryDto updateResource(long pResourceId, ResourceUpdateDto pResourceUpdateDto) {
        Resource vResource = this.getResourceById(pResourceId);
        ResourcesGroup vResourceGroup = resourcesGroupRepository.getResourcesGroupById(pResourceUpdateDto.getIdResourcesGroup()).orElse(null);
        Route vRoute = routeRepository.getRouteById(pResourceUpdateDto.getIdRoute()).orElse(null);
        BeanUtils.copyProperties(pResourceUpdateDto, vResource);
        vResource.setResourcesGroup(vResourceGroup);
        vResource.setRoute(vRoute);
        resourceRepository.save(vResource);
        ResourceQueryDto vResourceQueryDto = new ResourceQueryDto();
        BeanUtils.copyProperties(vResource, vResourceQueryDto);
        return vResourceQueryDto;
    }

    @Override
    public void deleteResource(long pResourceId) {
        Resource vResource = this.getResourceById(pResourceId);
        vResource.setDeletedDate(new Date());
        vResource.setState(Constants.STATE_DELETED);
        resourceRepository.save(vResource);
    }

    @Override
    public Resource getResourceById(long pResourceId) {
        Resource vResource = resourceRepository.getResourceById(pResourceId).orElse(null);
        if(vResource == null){
            Object[] obj = {"recurso", "id", String.valueOf(pResourceId)};
            throw Message.GetBadRequest(MessageDescription.notExists, obj);
        }
        return vResource;
    }

    @Override
    public Long[] getResourcesByRoles(String[] pRoles) {
        List<Resource> pResourceList = resourceRepository.getResourcesByRoles(pRoles);
        Long[] vResourceIdList = pResourceList.stream().map(x -> x.getId()).toArray(Long[]::new);
        return vResourceIdList;
    }
}
