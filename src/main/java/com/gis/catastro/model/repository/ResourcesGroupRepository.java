package com.gis.catastro.model.repository;

import com.gis.catastro.model.entity.ResourcesGroup;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Qualifier("ResourceGroupRepository")
public interface ResourcesGroupRepository extends CrudRepository<ResourcesGroup, Long>, QuerydslPredicateExecutor<ResourcesGroup> {
    @Query(value = "select count(d) from ResourcesGroup d where d.state=?1")
    Long getCountResourcesGroupByState(String pState);

    @Query(value = "select d from ResourcesGroup d where d.state=?1")
    List<ResourcesGroup> getResourcesGroupPageableByState(String pState, Pageable pPageable);

    @Query(value = "select d from ResourcesGroup d where d.id = ?1")
    Optional<ResourcesGroup> getResourcesGroupById(long pResourcesGroupId);
}
