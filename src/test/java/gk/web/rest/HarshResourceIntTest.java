package gk.web.rest;

import gk.AbstractCassandraTest;
import gk.GApp;

import gk.domain.Harsh;
import gk.repository.HarshRepository;
import gk.service.HarshService;
import gk.service.dto.HarshDTO;
import gk.service.mapper.HarshMapper;
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
 * Test class for the HarshResource REST controller.
 *
 * @see HarshResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GApp.class)
public class HarshResourceIntTest extends AbstractCassandraTest {

    private static final String DEFAULT_ARC = "AAAAAAAAAA";
    private static final String UPDATED_ARC = "BBBBBBBBBB";

    @Autowired
    private HarshRepository harshRepository;

    @Autowired
    private HarshMapper harshMapper;

    @Autowired
    private HarshService harshService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restHarshMockMvc;

    private Harsh harsh;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HarshResource harshResource = new HarshResource(harshService);
        this.restHarshMockMvc = MockMvcBuilders.standaloneSetup(harshResource)
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
    public static Harsh createEntity() {
        Harsh harsh = new Harsh()
            .arc(DEFAULT_ARC);
        return harsh;
    }

    @Before
    public void initTest() {
        harshRepository.deleteAll();
        harsh = createEntity();
    }

    @Test
    public void createHarsh() throws Exception {
        int databaseSizeBeforeCreate = harshRepository.findAll().size();

        // Create the Harsh
        HarshDTO harshDTO = harshMapper.toDto(harsh);
        restHarshMockMvc.perform(post("/api/harshes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(harshDTO)))
            .andExpect(status().isCreated());

        // Validate the Harsh in the database
        List<Harsh> harshList = harshRepository.findAll();
        assertThat(harshList).hasSize(databaseSizeBeforeCreate + 1);
        Harsh testHarsh = harshList.get(harshList.size() - 1);
        assertThat(testHarsh.getArc()).isEqualTo(DEFAULT_ARC);
    }

    @Test
    public void createHarshWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = harshRepository.findAll().size();

        // Create the Harsh with an existing ID
        harsh.setId(UUID.randomUUID());
        HarshDTO harshDTO = harshMapper.toDto(harsh);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHarshMockMvc.perform(post("/api/harshes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(harshDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Harsh> harshList = harshRepository.findAll();
        assertThat(harshList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkArcIsRequired() throws Exception {
        int databaseSizeBeforeTest = harshRepository.findAll().size();
        // set the field null
        harsh.setArc(null);

        // Create the Harsh, which fails.
        HarshDTO harshDTO = harshMapper.toDto(harsh);

        restHarshMockMvc.perform(post("/api/harshes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(harshDTO)))
            .andExpect(status().isBadRequest());

        List<Harsh> harshList = harshRepository.findAll();
        assertThat(harshList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllHarshes() throws Exception {
        // Initialize the database
        harshRepository.save(harsh);

        // Get all the harshList
        restHarshMockMvc.perform(get("/api/harshes"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(harsh.getId().toString())))
            .andExpect(jsonPath("$.[*].arc").value(hasItem(DEFAULT_ARC.toString())));
    }

    @Test
    public void getHarsh() throws Exception {
        // Initialize the database
        harshRepository.save(harsh);

        // Get the harsh
        restHarshMockMvc.perform(get("/api/harshes/{id}", harsh.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(harsh.getId().toString()))
            .andExpect(jsonPath("$.arc").value(DEFAULT_ARC.toString()));
    }

    @Test
    public void getNonExistingHarsh() throws Exception {
        // Get the harsh
        restHarshMockMvc.perform(get("/api/harshes/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateHarsh() throws Exception {
        // Initialize the database
        harshRepository.save(harsh);
        int databaseSizeBeforeUpdate = harshRepository.findAll().size();

        // Update the harsh
        Harsh updatedHarsh = harshRepository.findOne(harsh.getId());
        updatedHarsh
            .arc(UPDATED_ARC);
        HarshDTO harshDTO = harshMapper.toDto(updatedHarsh);

        restHarshMockMvc.perform(put("/api/harshes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(harshDTO)))
            .andExpect(status().isOk());

        // Validate the Harsh in the database
        List<Harsh> harshList = harshRepository.findAll();
        assertThat(harshList).hasSize(databaseSizeBeforeUpdate);
        Harsh testHarsh = harshList.get(harshList.size() - 1);
        assertThat(testHarsh.getArc()).isEqualTo(UPDATED_ARC);
    }

    @Test
    public void updateNonExistingHarsh() throws Exception {
        int databaseSizeBeforeUpdate = harshRepository.findAll().size();

        // Create the Harsh
        HarshDTO harshDTO = harshMapper.toDto(harsh);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHarshMockMvc.perform(put("/api/harshes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(harshDTO)))
            .andExpect(status().isCreated());

        // Validate the Harsh in the database
        List<Harsh> harshList = harshRepository.findAll();
        assertThat(harshList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteHarsh() throws Exception {
        // Initialize the database
        harshRepository.save(harsh);
        int databaseSizeBeforeDelete = harshRepository.findAll().size();

        // Get the harsh
        restHarshMockMvc.perform(delete("/api/harshes/{id}", harsh.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Harsh> harshList = harshRepository.findAll();
        assertThat(harshList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Harsh.class);
        Harsh harsh1 = new Harsh();
        harsh1.setId(UUID.randomUUID());
        Harsh harsh2 = new Harsh();
        harsh2.setId(harsh1.getId());
        assertThat(harsh1).isEqualTo(harsh2);
        harsh2.setId(UUID.randomUUID());
        assertThat(harsh1).isNotEqualTo(harsh2);
        harsh1.setId(null);
        assertThat(harsh1).isNotEqualTo(harsh2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HarshDTO.class);
        HarshDTO harshDTO1 = new HarshDTO();
        harshDTO1.setId(UUID.randomUUID());
        HarshDTO harshDTO2 = new HarshDTO();
        assertThat(harshDTO1).isNotEqualTo(harshDTO2);
        harshDTO2.setId(harshDTO1.getId());
        assertThat(harshDTO1).isEqualTo(harshDTO2);
        harshDTO2.setId(UUID.randomUUID());
        assertThat(harshDTO1).isNotEqualTo(harshDTO2);
        harshDTO1.setId(null);
        assertThat(harshDTO1).isNotEqualTo(harshDTO2);
    }
}
