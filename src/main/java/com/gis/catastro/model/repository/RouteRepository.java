package com.gis.catastro.model.repository;

import com.gis.catastro.model.entity.Route;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Qualifier("RouteRepository")
public interface RouteRepository extends CrudRepository<Route, Long> {
    @Query(value = "select count(d) from Route d")
    Long getCountRoutes();

    @Query(value = "select d from Route d")
    List<Route> getRoutesPageable(Pageable pPageable);

    @Query(value = "select d from Route d where d.id = ?1")
    Optional<Route> getRouteById(long pRouteId);

    @Query(value = "select d from Route d")
    List<Route> getRoutes();
}
