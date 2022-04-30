package com.gis.catastro.model.repository;

import com.gis.catastro.model.entity.Permission;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("PermissionRepository")
public interface PermissionRepository extends CrudRepository<Permission, Long> {
    @Query(value = "select d from Permission d inner join d.roles r where r.id = ?1")
    List<Permission> getPermissionsByRoleId(long pRoleId);

    @Query(value = "select d from Permission d where d.id in ?1")
    List<Permission> getPermissionsByIds(Long[] pIds);

    @Query(value = "select d from Permission d inner join d.roles r where r.role in ?1")
    List<Permission> getPermissionsByRoleIds(String[] pRoleIds);
}
