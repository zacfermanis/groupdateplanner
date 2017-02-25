package com.groupdateplanner.planner.service.impl;

import com.groupdateplanner.planner.service.PotentialEventDateService;
import com.groupdateplanner.planner.domain.PotentialEventDate;
import com.groupdateplanner.planner.repository.PotentialEventDateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing PotentialEventDate.
 */
@Service
@Transactional
public class PotentialEventDateServiceImpl implements PotentialEventDateService{

    private final Logger log = LoggerFactory.getLogger(PotentialEventDateServiceImpl.class);
    
    private final PotentialEventDateRepository potentialEventDateRepository;

    public PotentialEventDateServiceImpl(PotentialEventDateRepository potentialEventDateRepository) {
        this.potentialEventDateRepository = potentialEventDateRepository;
    }

    /**
     * Save a potentialEventDate.
     *
     * @param potentialEventDate the entity to save
     * @return the persisted entity
     */
    @Override
    public PotentialEventDate save(PotentialEventDate potentialEventDate) {
        log.debug("Request to save PotentialEventDate : {}", potentialEventDate);
        PotentialEventDate result = potentialEventDateRepository.save(potentialEventDate);
        return result;
    }

    /**
     *  Get all the potentialEventDates.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PotentialEventDate> findAll(Pageable pageable) {
        log.debug("Request to get all PotentialEventDates");
        Page<PotentialEventDate> result = potentialEventDateRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one potentialEventDate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PotentialEventDate findOne(Long id) {
        log.debug("Request to get PotentialEventDate : {}", id);
        PotentialEventDate potentialEventDate = potentialEventDateRepository.findOneWithEagerRelationships(id);
        return potentialEventDate;
    }

    /**
     *  Delete the  potentialEventDate by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PotentialEventDate : {}", id);
        potentialEventDateRepository.delete(id);
    }
}
