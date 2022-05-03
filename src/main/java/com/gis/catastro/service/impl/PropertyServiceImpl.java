package com.gis.catastro.service.impl;

import com.gis.catastro.model.entity.Property;
import com.gis.catastro.model.repository.PropertyRepository;
import com.gis.catastro.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    private PropertyRepository propertyRepository;
    @Override
    public Property getPredioByGeocodigo(String pGeocodigo) {
        Property vProperty = propertyRepository.getPropertyByGeocodigo(pGeocodigo).orElse(null);
        return vProperty;
    }
}
