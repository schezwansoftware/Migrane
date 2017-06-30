package gk.web.rest;

import gk.AbstractCassandraTest;
import gk.GApp;

import gk.domain.Rit;
import gk.repository.RitRepository;
import gk.service.RitService;
import gk.service.dto.RitDTO;
import gk.service.mapper.RitMapper;
import gk.web.rest.errors.ExceptionTranslator;

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

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RitResource REST controller.
 *
 * @see RitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GApp.class)
public class RitResourceIntTest extends AbstractCassandraTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private RitRepository ritRepository;

    @Autowired
    private RitMapper ritMapper;

    @Autowired
    private RitService ritService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restRitMockMvc;

    private Rit rit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RitResource ritResource = new RitResource(ritService);
        this.restRitMockMvc = MockMvcBuilders.standaloneSetup(ritResource)
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
    public static Rit createEntity() {
        Rit rit = new Rit()
            .name(DEFAULT_NAME);
        return rit;
    }

    @Before
    public void initTest() {
        ritRepository.deleteAll();
        rit = createEntity();
    }

    @Test
    public void createRit() throws Exception {
        int databaseSizeBeforeCreate = ritRepository.findAll().size();

        // Create the Rit
        RitDTO ritDTO = ritMapper.toDto(rit);
        restRitMockMvc.perform(post("/api/rits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ritDTO)))
            .andExpect(status().isCreated());

        // Validate the Rit in the database
        List<Rit> ritList = ritRepository.findAll();
        assertThat(ritList).hasSize(databaseSizeBeforeCreate + 1);
        Rit testRit = ritList.get(ritList.size() - 1);
        assertThat(testRit.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    public void createRitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ritRepository.findAll().size();

        // Create the Rit with an existing ID
        rit.setId(UUID.randomUUID());
        RitDTO ritDTO = ritMapper.toDto(rit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRitMockMvc.perform(post("/api/rits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ritDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Rit> ritList = ritRepository.findAll();
        assertThat(ritList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllRits() throws Exception {
        // Initialize the database
        ritRepository.save(rit);

        // Get all the ritList
        restRitMockMvc.perform(get("/api/rits"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rit.getId().toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    public void getRit() throws Exception {
        // Initialize the database
        ritRepository.save(rit);

        // Get the rit
        restRitMockMvc.perform(get("/api/rits/{id}", rit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rit.getId().toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    public void getNonExistingRit() throws Exception {
        // Get the rit
        restRitMockMvc.perform(get("/api/rits/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateRit() throws Exception {
        // Initialize the database
        ritRepository.save(rit);
        int databaseSizeBeforeUpdate = ritRepository.findAll().size();

        // Update the rit
        Rit updatedRit = ritRepository.findOne(rit.getId());
        updatedRit
            .name(UPDATED_NAME);
        RitDTO ritDTO = ritMapper.toDto(updatedRit);

        restRitMockMvc.perform(put("/api/rits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ritDTO)))
            .andExpect(status().isOk());

        // Validate the Rit in the database
        List<Rit> ritList = ritRepository.findAll();
        assertThat(ritList).hasSize(databaseSizeBeforeUpdate);
        Rit testRit = ritList.get(ritList.size() - 1);
        assertThat(testRit.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    public void updateNonExistingRit() throws Exception {
        int databaseSizeBeforeUpdate = ritRepository.findAll().size();

        // Create the Rit
        RitDTO ritDTO = ritMapper.toDto(rit);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRitMockMvc.perform(put("/api/rits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ritDTO)))
            .andExpect(status().isCreated());

        // Validate the Rit in the database
        List<Rit> ritList = ritRepository.findAll();
        assertThat(ritList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteRit() throws Exception {
        // Initialize the database
        ritRepository.save(rit);
        int databaseSizeBeforeDelete = ritRepository.findAll().size();

        // Get the rit
        restRitMockMvc.perform(delete("/api/rits/{id}", rit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Rit> ritList = ritRepository.findAll();
        assertThat(ritList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rit.class);
        Rit rit1 = new Rit();
        rit1.setId(UUID.randomUUID());
        Rit rit2 = new Rit();
        rit2.setId(rit1.getId());
        assertThat(rit1).isEqualTo(rit2);
        rit2.setId(UUID.randomUUID());
        assertThat(rit1).isNotEqualTo(rit2);
        rit1.setId(null);
        assertThat(rit1).isNotEqualTo(rit2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RitDTO.class);
        RitDTO ritDTO1 = new RitDTO();
        ritDTO1.setId(UUID.randomUUID());
        RitDTO ritDTO2 = new RitDTO();
        assertThat(ritDTO1).isNotEqualTo(ritDTO2);
        ritDTO2.setId(ritDTO1.getId());
        assertThat(ritDTO1).isEqualTo(ritDTO2);
        ritDTO2.setId(UUID.randomUUID());
        assertThat(ritDTO1).isNotEqualTo(ritDTO2);
        ritDTO1.setId(null);
        assertThat(ritDTO1).isNotEqualTo(ritDTO2);
    }
}
