package com.groupdateplanner.planner.repository;

import com.groupdateplanner.planner.domain.PotentialEventDate;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the PotentialEventDate entity.
 */
@SuppressWarnings("unused")
public interface PotentialEventDateRepository extends JpaRepository<PotentialEventDate,Long> {

    @Query("select distinct potentialEventDate from PotentialEventDate potentialEventDate left join fetch potentialEventDate.acceptedUsers")
    List<PotentialEventDate> findAllWithEagerRelationships();

    @Query("select potentialEventDate from PotentialEventDate potentialEventDate left join fetch potentialEventDate.acceptedUsers where potentialEventDate.id =:id")
    PotentialEventDate findOneWithEagerRelationships(@Param("id") Long id);

}
