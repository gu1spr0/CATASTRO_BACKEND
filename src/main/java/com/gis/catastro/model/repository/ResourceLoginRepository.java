package com.gis.catastro.model.repository;

import com.gis.catastro.model.entity.ResourceLogin;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("ResourceLoginRepository")
public interface ResourceLoginRepository extends CrudRepository<ResourceLogin, Long>, QuerydslPredicateExecutor<ResourceLogin> {
    @Query(value = "select d from ResourceLogin d where d.resourceId in ?1")
    List<ResourceLogin> getResourcesById(Long[] pResourceIdList);

    @Query(value = "select d from ResourceLogin d")
    List<ResourceLogin> getResources();
}
