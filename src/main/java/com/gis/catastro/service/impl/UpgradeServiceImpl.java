package com.gis.catastro.service.impl;

import com.gis.catastro.exception.Message;
import com.gis.catastro.exception.MessageDescription;
import com.gis.catastro.model.entity.Upgrade;
import com.gis.catastro.model.repository.UpgradeRepository;
import com.gis.catastro.service.UpgradeService;
import com.gis.catastro.service.dto.upgrade.UpgradeQueryDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UpgradeServiceImpl implements UpgradeService {
    @Autowired
    private UpgradeRepository upgradeRepository;
    @Override
    public List<UpgradeQueryDto> getUpgradesByGeocodigo(String pGeocodigo) {
        List<UpgradeQueryDto> vUpgradeQueryDtoList = new ArrayList<>();
        List<Upgrade> vUpgradeList = upgradeRepository.getUpgradeByGeocodigo(pGeocodigo);
        if(vUpgradeList.size() > 0){
            for(Upgrade vUpgrade : vUpgradeList){
                UpgradeQueryDto vUpgradeQueryDto = new UpgradeQueryDto();
                BeanUtils.copyProperties(vUpgrade,vUpgradeQueryDto);
                vUpgradeQueryDtoList.add(vUpgradeQueryDto);
            }
        }

        return vUpgradeQueryDtoList;
    }
}
