package com.groupdateplanner.planner.service;

import com.groupdateplanner.planner.domain.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Location.
 */
public interface LocationService {

    /**
     * Save a location.
     *
     * @param location the entity to save
     * @return the persisted entity
     */
    Location save(Location location);

    /**
     *  Get all the locations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Location> findAll(Pageable pageable);
    /**
     *  Get all the LocationDTO where Event is null.
     *
     *  @return the list of entities
     */
    List<Location> findAllWhereEventIsNull();

    /**
     *  Get the "id" location.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Location findOne(Long id);

    /**
     *  Delete the "id" location.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
