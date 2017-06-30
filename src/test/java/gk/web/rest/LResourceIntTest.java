package gk.web.rest;

import gk.AbstractCassandraTest;
import gk.GApp;

import gk.domain.L;
import gk.repository.LRepository;
import gk.service.LService;
import gk.service.dto.LDTO;
import gk.service.mapper.LMapper;
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
 * Test class for the LResource REST controller.
 *
 * @see LResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GApp.class)
public class LResourceIntTest extends AbstractCassandraTest {

    private static final Integer DEFAULT_LENGTH = 1;
    private static final Integer UPDATED_LENGTH = 2;

    private static final Integer DEFAULT_BREADTH = 1;
    private static final Integer UPDATED_BREADTH = 2;

    private static final Double DEFAULT_AREA = 1D;
    private static final Double UPDATED_AREA = 2D;

    @Autowired
    private LRepository lRepository;

    @Autowired
    private LMapper lMapper;

    @Autowired
    private LService lService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restLMockMvc;

    private L l;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LResource lResource = new LResource(lService);
        this.restLMockMvc = MockMvcBuilders.standaloneSetup(lResource)
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
    public static L createEntity() {
        L l = new L()
            .length(DEFAULT_LENGTH)
            .breadth(DEFAULT_BREADTH)
            .area(DEFAULT_AREA);
        return l;
    }

    @Before
    public void initTest() {
        lRepository.deleteAll();
        l = createEntity();
    }

    @Test
    public void createL() throws Exception {
        int databaseSizeBeforeCreate = lRepository.findAll().size();

        // Create the L
        LDTO lDTO = lMapper.toDto(l);
        restLMockMvc.perform(post("/api/ls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lDTO)))
            .andExpect(status().isCreated());

        // Validate the L in the database
        List<L> lList = lRepository.findAll();
        assertThat(lList).hasSize(databaseSizeBeforeCreate + 1);
        L testL = lList.get(lList.size() - 1);
        assertThat(testL.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testL.getBreadth()).isEqualTo(DEFAULT_BREADTH);
        assertThat(testL.getArea()).isEqualTo(DEFAULT_AREA);
    }

    @Test
    public void createLWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lRepository.findAll().size();

        // Create the L with an existing ID
        l.setId(UUID.randomUUID());
        LDTO lDTO = lMapper.toDto(l);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLMockMvc.perform(post("/api/ls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<L> lList = lRepository.findAll();
        assertThat(lList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkLengthIsRequired() throws Exception {
        int databaseSizeBeforeTest = lRepository.findAll().size();
        // set the field null
        l.setLength(null);

        // Create the L, which fails.
        LDTO lDTO = lMapper.toDto(l);

        restLMockMvc.perform(post("/api/ls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lDTO)))
            .andExpect(status().isBadRequest());

        List<L> lList = lRepository.findAll();
        assertThat(lList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkBreadthIsRequired() throws Exception {
        int databaseSizeBeforeTest = lRepository.findAll().size();
        // set the field null
        l.setBreadth(null);

        // Create the L, which fails.
        LDTO lDTO = lMapper.toDto(l);

        restLMockMvc.perform(post("/api/ls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lDTO)))
            .andExpect(status().isBadRequest());

        List<L> lList = lRepository.findAll();
        assertThat(lList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllLS() throws Exception {
        // Initialize the database
        lRepository.save(l);

        // Get all the lList
        restLMockMvc.perform(get("/api/ls"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(l.getId().toString())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH)))
            .andExpect(jsonPath("$.[*].breadth").value(hasItem(DEFAULT_BREADTH)))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA.doubleValue())));
    }

    @Test
    public void getL() throws Exception {
        // Initialize the database
        lRepository.save(l);

        // Get the l
        restLMockMvc.perform(get("/api/ls/{id}", l.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(l.getId().toString()))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH))
            .andExpect(jsonPath("$.breadth").value(DEFAULT_BREADTH))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA.doubleValue()));
    }

    @Test
    public void getNonExistingL() throws Exception {
        // Get the l
        restLMockMvc.perform(get("/api/ls/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateL() throws Exception {
        // Initialize the database
        lRepository.save(l);
        int databaseSizeBeforeUpdate = lRepository.findAll().size();

        // Update the l
        L updatedL = lRepository.findOne(l.getId());
        updatedL
            .length(UPDATED_LENGTH)
            .breadth(UPDATED_BREADTH)
            .area(UPDATED_AREA);
        LDTO lDTO = lMapper.toDto(updatedL);

        restLMockMvc.perform(put("/api/ls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lDTO)))
            .andExpect(status().isOk());

        // Validate the L in the database
        List<L> lList = lRepository.findAll();
        assertThat(lList).hasSize(databaseSizeBeforeUpdate);
        L testL = lList.get(lList.size() - 1);
        assertThat(testL.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testL.getBreadth()).isEqualTo(UPDATED_BREADTH);
        assertThat(testL.getArea()).isEqualTo(UPDATED_AREA);
    }

    @Test
    public void updateNonExistingL() throws Exception {
        int databaseSizeBeforeUpdate = lRepository.findAll().size();

        // Create the L
        LDTO lDTO = lMapper.toDto(l);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLMockMvc.perform(put("/api/ls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lDTO)))
            .andExpect(status().isCreated());

        // Validate the L in the database
        List<L> lList = lRepository.findAll();
        assertThat(lList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteL() throws Exception {
        // Initialize the database
        lRepository.save(l);
        int databaseSizeBeforeDelete = lRepository.findAll().size();

        // Get the l
        restLMockMvc.perform(delete("/api/ls/{id}", l.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<L> lList = lRepository.findAll();
        assertThat(lList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(L.class);
        L l1 = new L();
        l1.setId(UUID.randomUUID());
        L l2 = new L();
        l2.setId(l1.getId());
        assertThat(l1).isEqualTo(l2);
        l2.setId(UUID.randomUUID());
        assertThat(l1).isNotEqualTo(l2);
        l1.setId(null);
        assertThat(l1).isNotEqualTo(l2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LDTO.class);
        LDTO lDTO1 = new LDTO();
        lDTO1.setId(UUID.randomUUID());
        LDTO lDTO2 = new LDTO();
        assertThat(lDTO1).isNotEqualTo(lDTO2);
        lDTO2.setId(lDTO1.getId());
        assertThat(lDTO1).isEqualTo(lDTO2);
        lDTO2.setId(UUID.randomUUID());
        assertThat(lDTO1).isNotEqualTo(lDTO2);
        lDTO1.setId(null);
        assertThat(lDTO1).isNotEqualTo(lDTO2);
    }
}
