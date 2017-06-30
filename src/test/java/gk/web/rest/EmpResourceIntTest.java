package gk.web.rest;

import gk.AbstractCassandraTest;
import gk.GApp;

import gk.domain.Emp;
import gk.repository.EmpRepository;
import gk.service.EmpService;
import gk.service.dto.EmpDTO;
import gk.service.mapper.EmpMapper;
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
 * Test class for the EmpResource REST controller.
 *
 * @see EmpResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GApp.class)
public class EmpResourceIntTest extends AbstractCassandraTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    @Autowired
    private EmpRepository empRepository;

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private EmpService empService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restEmpMockMvc;

    private Emp emp;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmpResource empResource = new EmpResource(empService);
        this.restEmpMockMvc = MockMvcBuilders.standaloneSetup(empResource)
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
    public static Emp createEntity() {
        Emp emp = new Emp()
            .name(DEFAULT_NAME)
            .age(DEFAULT_AGE);
        return emp;
    }

    @Before
    public void initTest() {
        empRepository.deleteAll();
        emp = createEntity();
    }

    @Test
    public void createEmp() throws Exception {
        int databaseSizeBeforeCreate = empRepository.findAll().size();

        // Create the Emp
        EmpDTO empDTO = empMapper.toDto(emp);
        restEmpMockMvc.perform(post("/api/emps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empDTO)))
            .andExpect(status().isCreated());

        // Validate the Emp in the database
        List<Emp> empList = empRepository.findAll();
        assertThat(empList).hasSize(databaseSizeBeforeCreate + 1);
        Emp testEmp = empList.get(empList.size() - 1);
        assertThat(testEmp.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmp.getAge()).isEqualTo(DEFAULT_AGE);
    }

    @Test
    public void createEmpWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = empRepository.findAll().size();

        // Create the Emp with an existing ID
        emp.setId(UUID.randomUUID());
        EmpDTO empDTO = empMapper.toDto(emp);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpMockMvc.perform(post("/api/emps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Emp> empList = empRepository.findAll();
        assertThat(empList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = empRepository.findAll().size();
        // set the field null
        emp.setName(null);

        // Create the Emp, which fails.
        EmpDTO empDTO = empMapper.toDto(emp);

        restEmpMockMvc.perform(post("/api/emps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empDTO)))
            .andExpect(status().isBadRequest());

        List<Emp> empList = empRepository.findAll();
        assertThat(empList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkAgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = empRepository.findAll().size();
        // set the field null
        emp.setAge(null);

        // Create the Emp, which fails.
        EmpDTO empDTO = empMapper.toDto(emp);

        restEmpMockMvc.perform(post("/api/emps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empDTO)))
            .andExpect(status().isBadRequest());

        List<Emp> empList = empRepository.findAll();
        assertThat(empList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllEmps() throws Exception {
        // Initialize the database
        empRepository.save(emp);

        // Get all the empList
        restEmpMockMvc.perform(get("/api/emps"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emp.getId().toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)));
    }

    @Test
    public void getEmp() throws Exception {
        // Initialize the database
        empRepository.save(emp);

        // Get the emp
        restEmpMockMvc.perform(get("/api/emps/{id}", emp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(emp.getId().toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE));
    }

    @Test
    public void getNonExistingEmp() throws Exception {
        // Get the emp
        restEmpMockMvc.perform(get("/api/emps/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateEmp() throws Exception {
        // Initialize the database
        empRepository.save(emp);
        int databaseSizeBeforeUpdate = empRepository.findAll().size();

        // Update the emp
        Emp updatedEmp = empRepository.findOne(emp.getId());
        updatedEmp
            .name(UPDATED_NAME)
            .age(UPDATED_AGE);
        EmpDTO empDTO = empMapper.toDto(updatedEmp);

        restEmpMockMvc.perform(put("/api/emps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empDTO)))
            .andExpect(status().isOk());

        // Validate the Emp in the database
        List<Emp> empList = empRepository.findAll();
        assertThat(empList).hasSize(databaseSizeBeforeUpdate);
        Emp testEmp = empList.get(empList.size() - 1);
        assertThat(testEmp.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmp.getAge()).isEqualTo(UPDATED_AGE);
    }

    @Test
    public void updateNonExistingEmp() throws Exception {
        int databaseSizeBeforeUpdate = empRepository.findAll().size();

        // Create the Emp
        EmpDTO empDTO = empMapper.toDto(emp);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmpMockMvc.perform(put("/api/emps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empDTO)))
            .andExpect(status().isCreated());

        // Validate the Emp in the database
        List<Emp> empList = empRepository.findAll();
        assertThat(empList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteEmp() throws Exception {
        // Initialize the database
        empRepository.save(emp);
        int databaseSizeBeforeDelete = empRepository.findAll().size();

        // Get the emp
        restEmpMockMvc.perform(delete("/api/emps/{id}", emp.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Emp> empList = empRepository.findAll();
        assertThat(empList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Emp.class);
        Emp emp1 = new Emp();
        emp1.setId(UUID.randomUUID());
        Emp emp2 = new Emp();
        emp2.setId(emp1.getId());
        assertThat(emp1).isEqualTo(emp2);
        emp2.setId(UUID.randomUUID());
        assertThat(emp1).isNotEqualTo(emp2);
        emp1.setId(null);
        assertThat(emp1).isNotEqualTo(emp2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmpDTO.class);
        EmpDTO empDTO1 = new EmpDTO();
        empDTO1.setId(UUID.randomUUID());
        EmpDTO empDTO2 = new EmpDTO();
        assertThat(empDTO1).isNotEqualTo(empDTO2);
        empDTO2.setId(empDTO1.getId());
        assertThat(empDTO1).isEqualTo(empDTO2);
        empDTO2.setId(UUID.randomUUID());
        assertThat(empDTO1).isNotEqualTo(empDTO2);
        empDTO1.setId(null);
        assertThat(empDTO1).isNotEqualTo(empDTO2);
    }
}
