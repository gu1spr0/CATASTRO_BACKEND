package com.gis.catastro.model.repository;

import com.gis.catastro.model.entity.Upgrade;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Qualifier("UpgradeRepository")
public interface UpgradeRepository extends CrudRepository<Upgrade, Long> {
    @Query(value = "select id, st_transform(geom, 4326) as geom, objectid, referencia, mejora, observacio, geocodigo, shape_leng, shape_area from \"v_Mejoras\" where geocodigo =:pGeocodigo", nativeQuery = true)
    List<Upgrade> getUpgradeByGeocodigo(String pGeocodigo);
}
