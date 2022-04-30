package com.gis.catastro.model.repository;

import com.gis.catastro.model.entity.Resource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Qualifier("ResourceRepository")
public interface ResourceRepository extends CrudRepository<Resource, Long>, QuerydslPredicateExecutor<Resource> {
    @Query(value = "select count(d) from Resource d where d.resourcesGroup.id=?1 AND d.state=?2")
    Long getCountResourcesValuesByIdAndState(Long pIdRecursoGrupo, String pState);

    @Query(value = "select d from Resource d where d.resourcesGroup.id=?1 AND d.state=?2")
    List<Resource> getResourcesPageableByIdAndState(Long pIdRecursoGrupo, String pState, Pageable pPageable);

    @Query(value = "select d from Resource d where d.id = ?1")
    Optional<Resource> getResourceById(long pResourceId);

    @Query(value = "select d from Resource d inner join d.roles r where r.role in ?1")
    List<Resource> getResourcesByRoles(String[] pRoles);

    @Query(value = "select d from Resource d inner join d.roles r where r.id = ?1")
    List<Resource> getResourcesByRoleId(long pRoleId);

    @Query(value = "select d from Resource d where d.id in ?1")
    List<Resource> getResourcesByIds(Long[] pIds);
}
