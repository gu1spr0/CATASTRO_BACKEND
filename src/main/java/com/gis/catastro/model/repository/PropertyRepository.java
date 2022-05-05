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
    @Query(value = "select \"GEOCODIGO\", st_transform(geom, 4326) as geom, objectid, join_count, target_fid, referencia, est_gabine, referenc_1, n_grilla, contador, geocodigo, shape_leng, shape_area from \"v_Predios\" where geocodigo =:pGeocodigo", nativeQuery = true)
    Optional<Property> getPropertyByGeocodigo(String pGeocodigo);

}
