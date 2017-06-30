package gk.web.rest;

import gk.AbstractCassandraTest;
import gk.GApp;

import gk.domain.Ar;
import gk.repository.ArRepository;
import gk.service.ArService;
import gk.service.dto.ArDTO;
import gk.service.mapper.ArMapper;
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
 * Test class for the ArResource REST controller.
 *
 * @see ArResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GApp.class)
public class ArResourceIntTest extends AbstractCassandraTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ArRepository arRepository;

    @Autowired
    private ArMapper arMapper;

    @Autowired
    private ArService arService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restArMockMvc;

    private Ar ar;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ArResource arResource = new ArResource(arService);
        this.restArMockMvc = MockMvcBuilders.standaloneSetup(arResource)
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
    public static Ar createEntity() {
        Ar ar = new Ar()
            .name(DEFAULT_NAME);
        return ar;
    }

    @Before
    public void initTest() {
        arRepository.deleteAll();
        ar = createEntity();
    }

    @Test
    public void createAr() throws Exception {
        int databaseSizeBeforeCreate = arRepository.findAll().size();

        // Create the Ar
        ArDTO arDTO = arMapper.toDto(ar);
        restArMockMvc.perform(post("/api/ars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(arDTO)))
            .andExpect(status().isCreated());

        // Validate the Ar in the database
        List<Ar> arList = arRepository.findAll();
        assertThat(arList).hasSize(databaseSizeBeforeCreate + 1);
        Ar testAr = arList.get(arList.size() - 1);
        assertThat(testAr.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    public void createArWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = arRepository.findAll().size();

        // Create the Ar with an existing ID
        ar.setId(UUID.randomUUID());
        ArDTO arDTO = arMapper.toDto(ar);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArMockMvc.perform(post("/api/ars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(arDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Ar> arList = arRepository.findAll();
        assertThat(arList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllArs() throws Exception {
        // Initialize the database
        arRepository.save(ar);

        // Get all the arList
        restArMockMvc.perform(get("/api/ars"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ar.getId().toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    public void getAr() throws Exception {
        // Initialize the database
        arRepository.save(ar);

        // Get the ar
        restArMockMvc.perform(get("/api/ars/{id}", ar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ar.getId().toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    public void getNonExistingAr() throws Exception {
        // Get the ar
        restArMockMvc.perform(get("/api/ars/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAr() throws Exception {
        // Initialize the database
        arRepository.save(ar);
        int databaseSizeBeforeUpdate = arRepository.findAll().size();

        // Update the ar
        Ar updatedAr = arRepository.findOne(ar.getId());
        updatedAr
            .name(UPDATED_NAME);
        ArDTO arDTO = arMapper.toDto(updatedAr);

        restArMockMvc.perform(put("/api/ars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(arDTO)))
            .andExpect(status().isOk());

        // Validate the Ar in the database
        List<Ar> arList = arRepository.findAll();
        assertThat(arList).hasSize(databaseSizeBeforeUpdate);
        Ar testAr = arList.get(arList.size() - 1);
        assertThat(testAr.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    public void updateNonExistingAr() throws Exception {
        int databaseSizeBeforeUpdate = arRepository.findAll().size();

        // Create the Ar
        ArDTO arDTO = arMapper.toDto(ar);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restArMockMvc.perform(put("/api/ars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(arDTO)))
            .andExpect(status().isCreated());

        // Validate the Ar in the database
        List<Ar> arList = arRepository.findAll();
        assertThat(arList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteAr() throws Exception {
        // Initialize the database
        arRepository.save(ar);
        int databaseSizeBeforeDelete = arRepository.findAll().size();

        // Get the ar
        restArMockMvc.perform(delete("/api/ars/{id}", ar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ar> arList = arRepository.findAll();
        assertThat(arList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ar.class);
        Ar ar1 = new Ar();
        ar1.setId(UUID.randomUUID());
        Ar ar2 = new Ar();
        ar2.setId(ar1.getId());
        assertThat(ar1).isEqualTo(ar2);
        ar2.setId(UUID.randomUUID());
        assertThat(ar1).isNotEqualTo(ar2);
        ar1.setId(null);
        assertThat(ar1).isNotEqualTo(ar2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArDTO.class);
        ArDTO arDTO1 = new ArDTO();
        arDTO1.setId(UUID.randomUUID());
        ArDTO arDTO2 = new ArDTO();
        assertThat(arDTO1).isNotEqualTo(arDTO2);
        arDTO2.setId(arDTO1.getId());
        assertThat(arDTO1).isEqualTo(arDTO2);
        arDTO2.setId(UUID.randomUUID());
        assertThat(arDTO1).isNotEqualTo(arDTO2);
        arDTO1.setId(null);
        assertThat(arDTO1).isNotEqualTo(arDTO2);
    }
}
