package com.gis.catastro.service;

import com.gis.catastro.model.entity.Upgrade;
import com.gis.catastro.service.dto.upgrade.UpgradeQueryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UpgradeService {
    List<UpgradeQueryDto> getUpgradesByGeocodigo(String pGeocodigo);
}
