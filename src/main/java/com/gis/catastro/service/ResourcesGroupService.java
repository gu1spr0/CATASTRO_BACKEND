package com.gis.catastro.service;

import com.gis.catastro.model.entity.ResourcesGroup;
import com.gis.catastro.service.dto.resourcesGroup.ResourcesGroupAddDto;
import com.gis.catastro.service.dto.resourcesGroup.ResourcesGroupQueryDto;
import com.gis.catastro.service.dto.resourcesGroup.ResourcesGroupQueryPageableDto;
import com.gis.catastro.service.dto.resourcesGroup.ResourcesGroupUpdateDto;
import org.springframework.stereotype.Service;

@Service
public interface ResourcesGroupService {
    ResourcesGroupQueryPageableDto getResourceGroupPageable(String pState, int pPage, int pRowsNumber);
    ResourcesGroupQueryPageableDto findResourceGroupPageable(String pCriteria, String pState, int pPage, int pRowsNumber);
    ResourcesGroupQueryDto addResourcesGroup(ResourcesGroupAddDto pResourcesGroupAddDto);
    ResourcesGroupQueryDto updateResourcesGroup(long pResourcesGroupId, ResourcesGroupUpdateDto pResourcesGroupUpdateDto);
    void deleteResourcesGroup(long pResourcesGroupId);
    ResourcesGroup getResourcesGroupById(long pResourcesGroupId);
}
