package com.gis.catastro.model.repository;

import com.gis.catastro.model.entity.PermissionsGroup;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("PermissionsGroupRepository")
public interface PermissionsGroupRepository extends CrudRepository<PermissionsGroup, Long> {
}
