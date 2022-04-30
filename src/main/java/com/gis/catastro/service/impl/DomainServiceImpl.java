package com.gis.catastro.service.impl;

import com.gis.catastro.exception.Message;
import com.gis.catastro.exception.MessageDescription;
import com.gis.catastro.model.entity.Domain;
import com.gis.catastro.model.entity.DomainValue;
import com.gis.catastro.model.entity.QDomain;
import com.gis.catastro.model.repository.DomainRepository;
import com.gis.catastro.model.repository.DomainValueRepository;
import com.gis.catastro.service.DomainService;
import com.gis.catastro.service.dto.domain.DomainAddDto;
import com.gis.catastro.service.dto.domain.DomainQueryDto;
import com.gis.catastro.service.dto.domain.DomainQueryPageableDto;
import com.gis.catastro.service.dto.domain.DomainUpdateDto;
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
public class DomainServiceImpl implements DomainService {
    @Autowired
    private DomainRepository domainRepository;

    @Autowired
    private DomainValueRepository domainValueRepository;

    @Override
    public DomainQueryPageableDto getDomainPageable(String pState, int pPage, int pRowsNumber) {
        DomainQueryPageableDto vDomainQueryPageableDto = new DomainQueryPageableDto();
        Long vTotalRows = domainRepository.getCountDomainsByState(pState);

        if(Strings.isNullOrEmpty(pState)){
            throw Message.GetBadRequest(MessageDescription.stateNullOrEmpty);
        }

        if(!(pState.equals(Constants.STATE_ACTIVE) || pState.equals(Constants.STATE_DELETED) || pState.equals(Constants.STATE_BLOCKED))){
            Object[] obj = {pState};
            throw Message.GetBadRequest(MessageDescription.stateNotValid, obj);
        }

        if (vTotalRows>0){
            List<Domain> vDomainList = domainRepository.getDomainsPageableByState(pState, PageRequest.of(pPage, pRowsNumber));
            List<DomainQueryDto> vDomainQueryDtoList = new ArrayList<>();
            for (Domain vDomain: vDomainList){
                DomainQueryDto vDomainQueryDto = new DomainQueryDto();
                BeanUtils.copyProperties(vDomain, vDomainQueryDto);
                vDomainQueryDtoList.add(vDomainQueryDto);
            }
            vDomainQueryPageableDto.setTotalRows(vTotalRows);
            vDomainQueryPageableDto.setDomainQueryDtoList(vDomainQueryDtoList);
        }else{
            vDomainQueryPageableDto.setTotalRows(0);
        }
        return vDomainQueryPageableDto;
    }

    @Override
    public DomainQueryPageableDto findDomainPageable(String pCriteria, String pState, int pPage, int pRowsNumber) {
        DomainQueryPageableDto vDomainQueryPageableDto = new DomainQueryPageableDto();
        if(Strings.isNullOrEmpty(pState)){
            throw Message.GetBadRequest(MessageDescription.stateNullOrEmpty);
        }
        if(!(pState.equals(Constants.STATE_ACTIVE) || pState.equals(Constants.STATE_INACTIVE) || pState.equals(Constants.STATE_BLOCKED) || pState.equals(Constants.STATE_DELETED))){
            Object[] obj = {pState};
            throw Message.GetBadRequest(MessageDescription.stateNotValid, obj);
        }
        QDomain qDomain = QDomain.domain;
        BooleanExpression vCodeDomain = qDomain.codeDomain.contains(pCriteria);
        BooleanExpression vDescriptionDomain = qDomain.descriptionDomain.contains(pCriteria);
        BooleanExpression vNameDomain = qDomain.nameDomain.contains(pCriteria);
        BooleanExpression vState = qDomain.state.eq(pState);
        Predicate vPredicate = vCodeDomain.or(vDescriptionDomain).or(vNameDomain).and(vState);
        long vTotalRows = domainRepository.count(vPredicate);
        if (vTotalRows>0){
            List<Domain> vDomainList = Lists.newArrayList(domainRepository.findAll(vPredicate, PageRequest.of(pPage, pRowsNumber)));
            List<DomainQueryDto> vDomainQueryDtoList = new ArrayList<>();
            for (Domain vDomain: vDomainList){
                DomainQueryDto vDomainQueryDto = new DomainQueryDto();
                BeanUtils.copyProperties(vDomain, vDomainQueryDto);
                vDomainQueryDtoList.add(vDomainQueryDto);
            }
            vDomainQueryPageableDto.setTotalRows(vTotalRows);
            vDomainQueryPageableDto.setDomainQueryDtoList(vDomainQueryDtoList);
        }else{
            vDomainQueryPageableDto.setTotalRows(0);
        }
        return vDomainQueryPageableDto;
    }

    @Override
    public DomainQueryDto addDomain(DomainAddDto pDomainAddDto) {
        Domain vDomain = domainRepository.getDomainByCodeDomain(pDomainAddDto.getCodeDomain()).orElse(null);
        if(vDomain != null){
            Object[] obj = {"dominio", pDomainAddDto.getCodeDomain()};
            throw Message.GetBadRequest(MessageDescription.repeated, obj);
        }
        vDomain = new Domain();
        BeanUtils.copyProperties(pDomainAddDto, vDomain);
        domainRepository.save(vDomain);
        DomainQueryDto vDomainQueryDto = new DomainQueryDto();
        BeanUtils.copyProperties(vDomain, vDomainQueryDto);
        return vDomainQueryDto;
    }

    @Override
    public DomainQueryDto updateDomain(long pDomainId, DomainUpdateDto pDomainUpdateDto) {
        Domain vDomain = domainRepository.getDomainByCodeDomain(pDomainUpdateDto.getCodeDomain()).orElse(null);
        if(vDomain!= null && vDomain.getId()!=pDomainId){
            Object[] obj = {"dominio", pDomainUpdateDto.getCodeDomain()};
            throw Message.GetBadRequest(MessageDescription.repeated, obj);

        }

        vDomain = domainRepository.getDomainById(pDomainId).orElse(null);
        BeanUtils.copyProperties(pDomainUpdateDto, vDomain);
        domainRepository.save(vDomain);
        DomainQueryDto vDomainQueryDto = new DomainQueryDto();
        BeanUtils.copyProperties(vDomain, vDomainQueryDto);
        return vDomainQueryDto;
    }

    @Override
    public void deleteDomain(long pDomainId) {
        Date date = new Date();
        Domain vDomain = this.getDomainById(pDomainId);
        vDomain.setDeletedDate(date);
        vDomain.setState(Constants.STATE_DELETED);
        domainRepository.save(vDomain);
        List<DomainValue> vDomainValueList = domainValueRepository.getDomainValueByDomainId(vDomain.getId());
        for(DomainValue vDomainValue : vDomainValueList){
            vDomainValue.setDeletedDate(date);
            vDomainValue.setState(Constants.STATE_DELETED);
            domainValueRepository.save(vDomainValue);
        }
    }

    @Override
    public Domain getDomainById(long pDomainId) {
        Domain vDomain = domainRepository.getDomainById(pDomainId).orElse(null);
        if (vDomain == null){
            Object[] obj = {"dominio", "id", String.valueOf(pDomainId)};
            throw Message.GetBadRequest(MessageDescription.notExists, obj);
        }
        return vDomain;
    }
}
