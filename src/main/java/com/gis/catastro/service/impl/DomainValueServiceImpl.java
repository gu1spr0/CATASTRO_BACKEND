package com.gis.catastro.service.impl;

import com.gis.catastro.exception.Message;
import com.gis.catastro.exception.MessageDescription;
import com.gis.catastro.model.entity.Domain;
import com.gis.catastro.model.entity.DomainValue;
import com.gis.catastro.model.entity.QDomainValue;
import com.gis.catastro.model.repository.DomainRepository;
import com.gis.catastro.model.repository.DomainValueRepository;
import com.gis.catastro.service.DomainValueService;
import com.gis.catastro.service.dto.domainValue.*;
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
public class DomainValueServiceImpl implements DomainValueService {
    @Autowired
    private DomainRepository domainRepository;

    @Autowired
    private DomainValueRepository domainValueRepository;

    @Override
    public DomainValueQueryPageableDto getDomainValuePageable(Long pDomainId, String pState, int pPage, int pRowsNumber) {
        DomainValueQueryPageableDto vDomainValueQueryPageableDto = new DomainValueQueryPageableDto();
        Long vTotalRows = domainValueRepository.getCountDomainsValuesByIdAndState(pDomainId,pState);
        if(Strings.isNullOrEmpty(pState)){
            throw Message.GetBadRequest(MessageDescription.stateNullOrEmpty);
        }

        if(!(pState.equals(Constants.STATE_ACTIVE) || pState.equals(Constants.STATE_INACTIVE) || pState.equals(Constants.STATE_BLOCKED))){
            Object[] obj = {pState};
            throw Message.GetBadRequest(MessageDescription.stateNotValid, obj);
        }

        if(vTotalRows>0){
            List<DomainValue> vDomainValueList = domainValueRepository.getDomainsValuesPageableByIdAndState(pDomainId, pState, PageRequest.of(pPage,pRowsNumber));
            List<DomainValueQueryDto> vDomainValueQueryDtoList = new ArrayList<>();
            for(DomainValue vDomainValue : vDomainValueList){
                DomainValueQueryDto vDomainValueQueryDto = new DomainValueQueryDto();
                BeanUtils.copyProperties(vDomainValue, vDomainValueQueryDto);
                vDomainValueQueryDto.setIdDomain(vDomainValue.getDomain().getId());
                vDomainValueQueryDtoList.add(vDomainValueQueryDto);
            }
            vDomainValueQueryPageableDto.setTotalRows(vTotalRows);
            vDomainValueQueryPageableDto.setDomainValueQueryDtoList(vDomainValueQueryDtoList);
        }else{
            vDomainValueQueryPageableDto.setTotalRows(0);
        }
        return vDomainValueQueryPageableDto;
    }

    @Override
    public DomainValueQueryPageableDto findDomainValuePageable(Long pDomainId, String pCriteria, String pState, int pPage, int pRowsNumber) {
        DomainValueQueryPageableDto vDomainValueQueryPageableDto = new DomainValueQueryPageableDto();
        if(Strings.isNullOrEmpty(pState)){
            throw Message.GetBadRequest(MessageDescription.stateNullOrEmpty);
        }

        if(!(pState.equals(Constants.STATE_ACTIVE) || pState.equals(Constants.STATE_INACTIVE) || pState.equals(Constants.STATE_BLOCKED) || pState.equals(Constants.STATE_DELETED))){
            Object[] obj = {pState};
            throw Message.GetBadRequest(MessageDescription.stateNotValid, obj);
        }
        QDomainValue qDomainValue = QDomainValue.domainValue;
        BooleanExpression vCodeValue = qDomainValue.codeValue.contains(pCriteria);
        BooleanExpression vTitleDescription = qDomainValue.titleDescription.contains(pCriteria);
        BooleanExpression vDomain = qDomainValue.domain.id.eq(pDomainId);
        BooleanExpression vState = qDomainValue.state.eq(pState);
        Predicate vPredicate = vCodeValue.or(vTitleDescription).and(vDomain).and(vState);
        long vTotalRows = domainValueRepository.count(vPredicate);

        if(vTotalRows>0){
            List<DomainValue> vDomainValueList = Lists.newArrayList(domainValueRepository.findAll(vPredicate, PageRequest.of(pPage,pRowsNumber)));
            List<DomainValueQueryDto> vDomainValueQueryDtoList = new ArrayList<>();
            for(DomainValue vDomainValue : vDomainValueList){
                DomainValueQueryDto vDomainValueQueryDto = new DomainValueQueryDto();
                BeanUtils.copyProperties(vDomainValue, vDomainValueQueryDto);
                vDomainValueQueryDto.setIdDomain(vDomainValue.getDomain().getId());
                vDomainValueQueryDtoList.add(vDomainValueQueryDto);
            }
            vDomainValueQueryPageableDto.setTotalRows(vTotalRows);
            vDomainValueQueryPageableDto.setDomainValueQueryDtoList(vDomainValueQueryDtoList);
        }else{
            vDomainValueQueryPageableDto.setTotalRows(0);
        }
        return vDomainValueQueryPageableDto;
    }

    @Override
    public DomainValueQueryDto addDomainValue(DomainValueAddDto pDomainValueAddDto) {
        DomainValue vDomainValue = domainValueRepository.getDomainValueByDomainIdAndCodeValue(pDomainValueAddDto.getIdDomain(), pDomainValueAddDto.getCodeValue()).orElse(null);
        Domain vDomain = domainRepository.getDomainById(pDomainValueAddDto.getIdDomain()).orElse(null);
        if (vDomainValue != null){
            Object[] obj = {"dominio-valor", vDomainValue.getCodeValue()};
            throw Message.GetBadRequest(MessageDescription.repeated, obj);
        }
        if(vDomain == null){
            Object[] obj = {"dominio", vDomain.getId()};
            throw Message.GetBadRequest(MessageDescription.notExists, obj);
        }

        vDomainValue = new DomainValue();
        BeanUtils.copyProperties(pDomainValueAddDto, vDomainValue);
        vDomainValue.setDomain(vDomain);
        domainValueRepository.save(vDomainValue);
        DomainValueQueryDto vDomainValueQueryDto = new DomainValueQueryDto();
        BeanUtils.copyProperties(vDomainValue, vDomainValueQueryDto);
        return vDomainValueQueryDto;
    }

    @Override
    public DomainValueQueryDto updateDomainValue(long pDomainValueId, DomainValueUpdateDto pDomainValueUpdateDto) {
        DomainValue vDomainValue = this.getDomainValueById(pDomainValueId);
        Domain vDomain = domainRepository.getDomainById(pDomainValueUpdateDto.getIdDomain()).orElse(null);
        BeanUtils.copyProperties(pDomainValueUpdateDto, vDomainValue);
        vDomainValue.setDomain(vDomain);
        domainValueRepository.save(vDomainValue);
        DomainValueQueryDto vDomainValueQueryDto = new DomainValueQueryDto();
        BeanUtils.copyProperties(vDomainValue, vDomainValueQueryDto);
        return vDomainValueQueryDto;
    }

    @Override
    public void deleteDomainValue(long pDomainValueId) {
        DomainValue vDomainValue = this.getDomainValueById(pDomainValueId);
        vDomainValue.setDeletedDate(new Date());
        vDomainValue.setState(Constants.STATE_DELETED);
        domainValueRepository.save(vDomainValue);
    }

    @Override
    public DomainValue getDomainValueById(long pDomainValueId) {
        DomainValue vDomainValue = domainValueRepository.getDomainValueById(pDomainValueId).orElse(null);
        if(vDomainValue == null){
            Object[] obj = {"dominio-valor", "id", String.valueOf(pDomainValueId)};
            throw Message.GetBadRequest(MessageDescription.notExists, obj);
        }
        return vDomainValue;
    }

    @Override
    public DomainValue getDomainValueByDomainCodeAndCodeValue(String pDomainCode, String pCodeValue, String pState) {
        DomainValue vDomainValueCity = domainValueRepository.getDomainValueByDomainCodeAndCodeValue(pDomainCode, pCodeValue, pState).orElse(null);
        if (vDomainValueCity == null) {
            Object[] obj = {pDomainCode, "codigo valor", pCodeValue};
            throw Message.GetBadRequest(MessageDescription.notExists, obj);
        }
        return vDomainValueCity;
    }

    @Override
    public DomainValue getDomainValueByDomainCodeAndCharValue(String pDomainCode, String pCodeValue, String pState) {
        DomainValue vDomainValueCity = domainValueRepository.getDomainValueByDomainCodeAndCharValue(pDomainCode, pCodeValue, pState).orElse(null);
        if (vDomainValueCity == null) {
            Object[] obj = {pDomainCode, "código valor", pCodeValue};
            throw Message.GetBadRequest(MessageDescription.notExists, obj);
        }
        return vDomainValueCity;
    }

    @Override
    public List<DomainValueQuerySelectDto> getDomainValueSelectByDomainCode(String pDomainCode) {
        List<DomainValueQuerySelectDto> vDomainValueQuerySelectDtoList = new ArrayList<>();
        List<DomainValue> vDomainValueList = domainValueRepository.getDomainValueByDomainCodeAndState(pDomainCode, Constants.STATE_ACTIVE);
        for (DomainValue vDomainValue: vDomainValueList){
            DomainValueQuerySelectDto vDomainValueQuerySelectDto = new DomainValueQuerySelectDto();
            BeanUtils.copyProperties(vDomainValue, vDomainValueQuerySelectDto);
            vDomainValueQuerySelectDtoList.add(vDomainValueQuerySelectDto);
        }
        return vDomainValueQuerySelectDtoList;
    }

    @Override
    public List<DomainValueQuerySelectDto> getRelation(String pDomainCode, String pCharValue, String pCharValueExtra) {
        List<DomainValueQuerySelectDto> vDomainValueQuerySelectDtoList = new ArrayList<>();
        List<DomainValue> vDomainValueList;
        if(Strings.isNullOrEmpty(pCharValueExtra) && !(Strings.isNullOrEmpty(pCharValue))){
            vDomainValueList = domainValueRepository.getDomainValueByDomainCodeAndCharValueAndState(pDomainCode, pCharValue, Constants.STATE_ACTIVE);
        }else{
            vDomainValueList = domainValueRepository.getDomainValueByDomainCodeAndCharValueExtraAndState(pDomainCode,pCharValueExtra,Constants.STATE_ACTIVE);
        }
        for (DomainValue vDomainValue: vDomainValueList){
            DomainValueQuerySelectDto vDomainValueQuerySelectDto = new DomainValueQuerySelectDto();
            BeanUtils.copyProperties(vDomainValue, vDomainValueQuerySelectDto);
            vDomainValueQuerySelectDtoList.add(vDomainValueQuerySelectDto);
        }
        return vDomainValueQuerySelectDtoList;
    }

    @Override
    public List<DomainValue> getDomainValuesByDomainCode(String pDomainCode) {
        List<DomainValue> vDomainValueList = domainValueRepository.getDomainValueByDomainCodeAndState(pDomainCode, Constants.STATE_ACTIVE);
        return vDomainValueList;
    }

    @Override
    public List<DomainValue> getDomainValueListByIds(Long[] pDomainValueIds) {
        List<DomainValue> vDomainValueList = domainValueRepository.getDomainValuesByIds(pDomainValueIds);
        return vDomainValueList;
    }

    @Override
    public List<DomainValue> getDomainValueListByCodeValues(String pDomainCode, String[] pCodeValues) {
        List<DomainValue> vDomainValueList = domainValueRepository.getDomainValuesByDomainAndCodeValues(pDomainCode, pCodeValues, Constants.STATE_ACTIVE);
        return vDomainValueList;
    }
}
