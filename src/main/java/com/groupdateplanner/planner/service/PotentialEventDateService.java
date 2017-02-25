package com.groupdateplanner.planner.service;

import com.groupdateplanner.planner.domain.PotentialEventDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PotentialEventDate.
 */
public interface PotentialEventDateService {

    /**
     * Save a potentialEventDate.
     *
     * @param potentialEventDate the entity to save
     * @return the persisted entity
     */
    PotentialEventDate save(PotentialEventDate potentialEventDate);

    /**
     *  Get all the potentialEventDates.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PotentialEventDate> findAll(Pageable pageable);

    /**
     *  Get the "id" potentialEventDate.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PotentialEventDate findOne(Long id);

    /**
     *  Delete the "id" potentialEventDate.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
