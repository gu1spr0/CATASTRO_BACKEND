package com.gis.catastro.model.repository;

import com.gis.catastro.model.entity.PermissionLogin;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("PermissionLoginRepository")
public interface PermissionLoginRepository extends CrudRepository<PermissionLogin, Long> {
    @Query(value = "select d from PermissionLogin d")
    List<PermissionLogin> getPermissions();
}
