package com.gis.catastro.model.repository;

import com.gis.catastro.model.entity.Property;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Qualifier("PropertyRepository")
public interface PropertyRepository extends CrudRepository<Property, Long> {
    @Query(value = "select p as geometria from Property p where p.geocodigo=?1")
    Optional<Property> getPropertyByGeocodigo(String pGeocodigo);
}
