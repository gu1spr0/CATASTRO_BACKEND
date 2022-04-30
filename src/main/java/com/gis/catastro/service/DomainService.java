package com.gis.catastro.service;

import com.gis.catastro.model.entity.Domain;
import com.gis.catastro.service.dto.domain.DomainAddDto;
import com.gis.catastro.service.dto.domain.DomainQueryDto;
import com.gis.catastro.service.dto.domain.DomainQueryPageableDto;
import com.gis.catastro.service.dto.domain.DomainUpdateDto;
import org.springframework.stereotype.Service;

@Service
public interface DomainService {
    DomainQueryPageableDto getDomainPageable(String pState, int pPage, int pRowsNumber);
    DomainQueryPageableDto findDomainPageable(String pCriteria, String pState, int pPage, int pRowsNumber);
    DomainQueryDto addDomain(DomainAddDto pDomainAddDto);
    DomainQueryDto updateDomain(long pDomainId, DomainUpdateDto pDomainUpdateDto);
    void deleteDomain(long pDomainId);
    Domain getDomainById(long pDomainId);
}
