package com.groupdateplanner.planner.repository;

import com.groupdateplanner.planner.domain.Event;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Event entity.
 */
@SuppressWarnings("unused")
public interface EventRepository extends JpaRepository<Event,Long> {

    @Query("select distinct event from Event event left join fetch event.invitedUsers left join fetch event.potentialEventDates")
    List<Event> findAllWithEagerRelationships();

    @Query("select event from Event event left join fetch event.invitedUsers left join fetch event.potentialEventDates where event.id =:id")
    Event findOneWithEagerRelationships(@Param("id") Long id);

}
