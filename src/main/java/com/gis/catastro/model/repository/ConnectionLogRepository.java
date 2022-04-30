package com.gis.catastro.model.repository;

import com.gis.catastro.model.entity.ConnectionLog;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Qualifier("ConnectionLogRepository")
public interface ConnectionLogRepository extends CrudRepository<ConnectionLog, Long> {
    @Query(value = "select d from ConnectionLog d where d.username=?1 and d.logoutDate is null")
    Optional<ConnectionLog> getConnectionLogByUsername(String pUsername);
}
