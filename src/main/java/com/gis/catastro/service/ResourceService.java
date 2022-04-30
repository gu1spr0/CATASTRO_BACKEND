package com.gis.catastro.service;

import com.gis.catastro.model.entity.Resource;
import com.gis.catastro.service.dto.resource.ResourceAddDto;
import com.gis.catastro.service.dto.resource.ResourceQueryDto;
import com.gis.catastro.service.dto.resource.ResourceQueryPageableDto;
import com.gis.catastro.service.dto.resource.ResourceUpdateDto;
import org.springframework.stereotype.Service;

@Service
public interface ResourceService {
    ResourceQueryPageableDto getResourcePageable(Long pResourceGroupId, String pState, int pPage, int pRowsNumber);
    ResourceQueryPageableDto findResourcePageable(Long pResourceGroupId, String pCriteria, String pState, int pPage, int pRowsNumber);
    ResourceQueryDto addResource(ResourceAddDto pResourceAddDto);
    ResourceQueryDto updateResource(long pResourceId, ResourceUpdateDto pResourceUpdateDto);
    void deleteResource(long pResourceId);
    Resource getResourceById(long pResourceId);
    Long[] getResourcesByRoles(String[] pRoles);
}
