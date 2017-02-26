package com.groupdateplanner.planner.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.groupdateplanner.planner.domain.Event;
import com.groupdateplanner.planner.domain.PotentialEventDate;
import com.groupdateplanner.planner.domain.User;
import com.groupdateplanner.planner.service.MailService;
import com.groupdateplanner.planner.service.PotentialEventDateService;
import com.groupdateplanner.planner.service.UserService;
import com.groupdateplanner.planner.web.rest.util.HeaderUtil;
import com.groupdateplanner.planner.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing PotentialEventDate.
 */
@RestController
@RequestMapping("/api")
public class PotentialEventDateResource {

    private final Logger log = LoggerFactory.getLogger(PotentialEventDateResource.class);

    private static final String ENTITY_NAME = "potentialEventDate";

    private final PotentialEventDateService potentialEventDateService;

    private final UserService userService;

    private final MailService mailService;

    public PotentialEventDateResource(PotentialEventDateService potentialEventDateService, UserService userService, MailService mailService) {
        this.potentialEventDateService = potentialEventDateService;
        this.userService = userService;
        this.mailService = mailService;
    }

    /**
     * POST  /potential-event-dates : Create a new potentialEventDate.
     *
     * @param potentialEventDate the potentialEventDate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new potentialEventDate, or with status 400 (Bad Request) if the potentialEventDate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/potential-event-dates")
    @Timed
    public ResponseEntity<PotentialEventDate> createPotentialEventDate(@Valid @RequestBody PotentialEventDate potentialEventDate) throws URISyntaxException {
        log.debug("REST request to save PotentialEventDate : {}", potentialEventDate);
        if (potentialEventDate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new potentialEventDate cannot already have an ID")).body(null);
        }
        PotentialEventDate result = potentialEventDateService.save(potentialEventDate);
        return ResponseEntity.created(new URI("/api/potential-event-dates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /potential-event-dates : Updates an existing potentialEventDate.
     *
     * @param potentialEventDate the potentialEventDate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated potentialEventDate,
     * or with status 400 (Bad Request) if the potentialEventDate is not valid,
     * or with status 500 (Internal Server Error) if the potentialEventDate couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/potential-event-dates")
    @Timed
    public ResponseEntity<PotentialEventDate> updatePotentialEventDate(@Valid @RequestBody PotentialEventDate potentialEventDate) throws URISyntaxException {
        log.debug("REST request to update PotentialEventDate : {}", potentialEventDate);
        if (potentialEventDate.getId() == null) {
            return createPotentialEventDate(potentialEventDate);
        }
        PotentialEventDate result = potentialEventDateService.save(potentialEventDate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, potentialEventDate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /potential-event-dates : get all the potentialEventDates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of potentialEventDates in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/potential-event-dates")
    @Timed
    public ResponseEntity<List<PotentialEventDate>> getAllPotentialEventDates(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PotentialEventDates");
        Page<PotentialEventDate> page = potentialEventDateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/potential-event-dates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /potential-event-dates/:id : get the "id" potentialEventDate.
     *
     * @param id the id of the potentialEventDate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the potentialEventDate, or with status 404 (Not Found)
     */
    @GetMapping("/potential-event-dates/{id}")
    @Timed
    public ResponseEntity<PotentialEventDate> getPotentialEventDate(@PathVariable Long id) {
        log.debug("REST request to get PotentialEventDate : {}", id);
        PotentialEventDate potentialEventDate = potentialEventDateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(potentialEventDate));
    }

    @PostMapping("/potential-event-dates/{id}/user/{userId}")
    @Timed
    public ResponseEntity<PotentialEventDate> vote(@PathVariable Long id, @PathVariable Long userId) {
        log.debug("REST request to vote for PotentialEventDate : {}, by User: {}", id, userId);
        PotentialEventDate potentialEventDate = potentialEventDateService.findOne(id);
        User user = userService.getUserWithAuthorities(userId);
        potentialEventDate.addAcceptedUser(user);
        PotentialEventDate result = potentialEventDateService.save(potentialEventDate);
        // TODO - Email Owner
//        Set<Event> events = potentialEventDate.getEvents();
//        User owner;
//        if (events.iterator().hasNext()) {
//            owner = userService.getUserWithAuthorities(events.iterator().next().get
//        }
        //Send mail to the owner
        //mailService.sendVotedEmail();
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, potentialEventDate.getId().toString()))
            .body(result);
    }

    /**
     * DELETE  /potential-event-dates/:id : delete the "id" potentialEventDate.
     *
     * @param id the id of the potentialEventDate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/potential-event-dates/{id}")
    @Timed
    public ResponseEntity<Void> deletePotentialEventDate(@PathVariable Long id) {
        log.debug("REST request to delete PotentialEventDate : {}", id);
        potentialEventDateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
