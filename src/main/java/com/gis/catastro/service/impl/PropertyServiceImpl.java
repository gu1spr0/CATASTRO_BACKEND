package com.gis.catastro.service.impl;

import com.gis.catastro.exception.Message;
import com.gis.catastro.exception.MessageDescription;
import com.gis.catastro.model.entity.Property;
import com.gis.catastro.model.repository.PropertyRepository;
import com.gis.catastro.service.PropertyService;
import com.gis.catastro.service.dto.property.PropertyQueryDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    private PropertyRepository propertyRepository;
    @Override
    public Property getPredioByGeocodigo(String pGeocodigo) {
        PropertyQueryDto vPropertyQueryDto = new PropertyQueryDto();
        Property vProperty = propertyRepository.getPropertyByGeocodigo(pGeocodigo).orElse(null);
        if(vProperty == null){
            Object[] obj = {"Predio", "Geocodigo", pGeocodigo};
            throw Message.GetBadRequest(MessageDescription.notExists, obj);
        }
        //BeanUtils.copyProperties(vProperty, vPropertyQueryDto);
        return vProperty;
    }
}
