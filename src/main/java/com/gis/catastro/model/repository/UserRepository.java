package com.gis.catastro.model.repository;

import com.gis.catastro.model.entity.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Qualifier("UserRepository")
public interface UserRepository extends CrudRepository<User, Long>, QuerydslPredicateExecutor<User> {
    @Query(value = "select count(u) from User u where u.state=?1")
    Long getCountUsersByState(String pState);

    @Query(value = "select u from User u where u.state=?1")
    List<User> getUsersPageableByState(String pState, Pageable pPageable);

    @Query(value = "select u from User u where u.username=?1 and u.deletedDate is null and u.state <> 'BLOQUEADO'")
    Optional<User> getUserByUsername(String pUserName);

    @Query(value = "select d from User d where d.id = ?1")
    Optional<User> getUserById(long pUserId);

    @Query(value = "select case when count(r) > 0 then true else false end from User r where r.username=?1 and r.deletedDate is null")
    Boolean isUserNameRepeated(String pUserName);
}
