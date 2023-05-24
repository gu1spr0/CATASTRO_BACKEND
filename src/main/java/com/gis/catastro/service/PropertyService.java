package com.gis.catastro.service;

import com.gis.catastro.model.entity.Property;
import com.gis.catastro.service.dto.property.PropertyQueryDto;
import org.springframework.stereotype.Service;

@Service
public interface PropertyService {
    Property getPredioByGeocodigo(String pGeocodigo);
}
