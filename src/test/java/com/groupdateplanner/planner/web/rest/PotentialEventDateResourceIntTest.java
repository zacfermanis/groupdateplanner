package com.groupdateplanner.planner.web.rest;

import com.groupdateplanner.planner.GroupdateplannerApp;

import com.groupdateplanner.planner.domain.PotentialEventDate;
import com.groupdateplanner.planner.repository.PotentialEventDateRepository;
import com.groupdateplanner.planner.service.PotentialEventDateService;
import com.groupdateplanner.planner.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.groupdateplanner.planner.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PotentialEventDateResource REST controller.
 *
 * @see PotentialEventDateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GroupdateplannerApp.class)
public class PotentialEventDateResourceIntTest {

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_TOTAL_ACCEPTED = 1;
    private static final Integer UPDATED_TOTAL_ACCEPTED = 2;

    private static final Integer DEFAULT_TOTAL_INVITED = 1;
    private static final Integer UPDATED_TOTAL_INVITED = 2;

    @Autowired
    private PotentialEventDateRepository potentialEventDateRepository;

    @Autowired
    private PotentialEventDateService potentialEventDateService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPotentialEventDateMockMvc;

    private PotentialEventDate potentialEventDate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PotentialEventDateResource potentialEventDateResource = new PotentialEventDateResource(potentialEventDateService);
        this.restPotentialEventDateMockMvc = MockMvcBuilders.standaloneSetup(potentialEventDateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PotentialEventDate createEntity(EntityManager em) {
        PotentialEventDate potentialEventDate = new PotentialEventDate()
                .startDate(DEFAULT_START_DATE)
                .endDate(DEFAULT_END_DATE)
                .totalAccepted(DEFAULT_TOTAL_ACCEPTED)
                .totalInvited(DEFAULT_TOTAL_INVITED);
        return potentialEventDate;
    }

    @Before
    public void initTest() {
        potentialEventDate = createEntity(em);
    }

    @Test
    @Transactional
    public void createPotentialEventDate() throws Exception {
        int databaseSizeBeforeCreate = potentialEventDateRepository.findAll().size();

        // Create the PotentialEventDate

        restPotentialEventDateMockMvc.perform(post("/api/potential-event-dates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(potentialEventDate)))
            .andExpect(status().isCreated());

        // Validate the PotentialEventDate in the database
        List<PotentialEventDate> potentialEventDateList = potentialEventDateRepository.findAll();
        assertThat(potentialEventDateList).hasSize(databaseSizeBeforeCreate + 1);
        PotentialEventDate testPotentialEventDate = potentialEventDateList.get(potentialEventDateList.size() - 1);
        assertThat(testPotentialEventDate.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPotentialEventDate.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testPotentialEventDate.getTotalAccepted()).isEqualTo(DEFAULT_TOTAL_ACCEPTED);
        assertThat(testPotentialEventDate.getTotalInvited()).isEqualTo(DEFAULT_TOTAL_INVITED);
    }

    @Test
    @Transactional
    public void createPotentialEventDateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = potentialEventDateRepository.findAll().size();

        // Create the PotentialEventDate with an existing ID
        PotentialEventDate existingPotentialEventDate = new PotentialEventDate();
        existingPotentialEventDate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPotentialEventDateMockMvc.perform(post("/api/potential-event-dates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPotentialEventDate)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PotentialEventDate> potentialEventDateList = potentialEventDateRepository.findAll();
        assertThat(potentialEventDateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = potentialEventDateRepository.findAll().size();
        // set the field null
        potentialEventDate.setStartDate(null);

        // Create the PotentialEventDate, which fails.

        restPotentialEventDateMockMvc.perform(post("/api/potential-event-dates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(potentialEventDate)))
            .andExpect(status().isBadRequest());

        List<PotentialEventDate> potentialEventDateList = potentialEventDateRepository.findAll();
        assertThat(potentialEventDateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = potentialEventDateRepository.findAll().size();
        // set the field null
        potentialEventDate.setEndDate(null);

        // Create the PotentialEventDate, which fails.

        restPotentialEventDateMockMvc.perform(post("/api/potential-event-dates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(potentialEventDate)))
            .andExpect(status().isBadRequest());

        List<PotentialEventDate> potentialEventDateList = potentialEventDateRepository.findAll();
        assertThat(potentialEventDateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPotentialEventDates() throws Exception {
        // Initialize the database
        potentialEventDateRepository.saveAndFlush(potentialEventDate);

        // Get all the potentialEventDateList
        restPotentialEventDateMockMvc.perform(get("/api/potential-event-dates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(potentialEventDate.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].totalAccepted").value(hasItem(DEFAULT_TOTAL_ACCEPTED)))
            .andExpect(jsonPath("$.[*].totalInvited").value(hasItem(DEFAULT_TOTAL_INVITED)));
    }

    @Test
    @Transactional
    public void getPotentialEventDate() throws Exception {
        // Initialize the database
        potentialEventDateRepository.saveAndFlush(potentialEventDate);

        // Get the potentialEventDate
        restPotentialEventDateMockMvc.perform(get("/api/potential-event-dates/{id}", potentialEventDate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(potentialEventDate.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.totalAccepted").value(DEFAULT_TOTAL_ACCEPTED))
            .andExpect(jsonPath("$.totalInvited").value(DEFAULT_TOTAL_INVITED));
    }

    @Test
    @Transactional
    public void getNonExistingPotentialEventDate() throws Exception {
        // Get the potentialEventDate
        restPotentialEventDateMockMvc.perform(get("/api/potential-event-dates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePotentialEventDate() throws Exception {
        // Initialize the database
        potentialEventDateService.save(potentialEventDate);

        int databaseSizeBeforeUpdate = potentialEventDateRepository.findAll().size();

        // Update the potentialEventDate
        PotentialEventDate updatedPotentialEventDate = potentialEventDateRepository.findOne(potentialEventDate.getId());
        updatedPotentialEventDate
                .startDate(UPDATED_START_DATE)
                .endDate(UPDATED_END_DATE)
                .totalAccepted(UPDATED_TOTAL_ACCEPTED)
                .totalInvited(UPDATED_TOTAL_INVITED);

        restPotentialEventDateMockMvc.perform(put("/api/potential-event-dates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPotentialEventDate)))
            .andExpect(status().isOk());

        // Validate the PotentialEventDate in the database
        List<PotentialEventDate> potentialEventDateList = potentialEventDateRepository.findAll();
        assertThat(potentialEventDateList).hasSize(databaseSizeBeforeUpdate);
        PotentialEventDate testPotentialEventDate = potentialEventDateList.get(potentialEventDateList.size() - 1);
        assertThat(testPotentialEventDate.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPotentialEventDate.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPotentialEventDate.getTotalAccepted()).isEqualTo(UPDATED_TOTAL_ACCEPTED);
        assertThat(testPotentialEventDate.getTotalInvited()).isEqualTo(UPDATED_TOTAL_INVITED);
    }

    @Test
    @Transactional
    public void updateNonExistingPotentialEventDate() throws Exception {
        int databaseSizeBeforeUpdate = potentialEventDateRepository.findAll().size();

        // Create the PotentialEventDate

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPotentialEventDateMockMvc.perform(put("/api/potential-event-dates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(potentialEventDate)))
            .andExpect(status().isCreated());

        // Validate the PotentialEventDate in the database
        List<PotentialEventDate> potentialEventDateList = potentialEventDateRepository.findAll();
        assertThat(potentialEventDateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePotentialEventDate() throws Exception {
        // Initialize the database
        potentialEventDateService.save(potentialEventDate);

        int databaseSizeBeforeDelete = potentialEventDateRepository.findAll().size();

        // Get the potentialEventDate
        restPotentialEventDateMockMvc.perform(delete("/api/potential-event-dates/{id}", potentialEventDate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PotentialEventDate> potentialEventDateList = potentialEventDateRepository.findAll();
        assertThat(potentialEventDateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PotentialEventDate.class);
    }
}
