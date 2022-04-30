package com.gis.catastro.service;

import com.gis.catastro.model.entity.DomainValue;
import com.gis.catastro.service.dto.domainValue.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DomainValueService {
    DomainValueQueryPageableDto getDomainValuePageable(Long pDomainId, String pState, int pPage, int pRowsNumber);
    DomainValueQueryPageableDto findDomainValuePageable(Long pDomainId, String pCriteria, String pState, int pPage, int pRowsNumber);
    DomainValueQueryDto addDomainValue(DomainValueAddDto pDomainValueAddDto);
    DomainValueQueryDto updateDomainValue(long pDomainValueId, DomainValueUpdateDto pDomainValueUpdateDto);
    void deleteDomainValue(long pDomainValueId);
    DomainValue getDomainValueById(long pDomainValueId);
    DomainValue getDomainValueByDomainCodeAndCodeValue(String pDomainCode, String pCodeValue, String pState);
    DomainValue getDomainValueByDomainCodeAndCharValue(String pDomainCode, String pCodeValue, String pState);
    List<DomainValueQuerySelectDto> getDomainValueSelectByDomainCode(String pDomainCode);
    List<DomainValueQuerySelectDto> getRelation(String pDomainCode, String pCharValue, String pCharValueExtra);
    List<DomainValue> getDomainValuesByDomainCode(String pDomainCode);
    List<DomainValue> getDomainValueListByIds(Long[] pDomainValueIds);
    List<DomainValue> getDomainValueListByCodeValues(String pDomainCode, String[] pCodeValues);//Set<String> pCodeValues);
}
