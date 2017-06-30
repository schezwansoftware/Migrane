package gk.web.rest;

import com.codahale.metrics.annotation.Timed;
import gk.service.EmpService;
import gk.web.rest.util.HeaderUtil;
import gk.service.dto.EmpDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST controller for managing Emp.
 */
@RestController
@RequestMapping("/api")
public class EmpResource {

    private final Logger log = LoggerFactory.getLogger(EmpResource.class);

    private static final String ENTITY_NAME = "emp";

    private final EmpService empService;

    public EmpResource(EmpService empService) {
        this.empService = empService;
    }

    /**
     * POST  /emps : Create a new emp.
     *
     * @param empDTO the empDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new empDTO, or with status 400 (Bad Request) if the emp has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/emps")
    @Timed
    public ResponseEntity<EmpDTO> createEmp(@Valid @RequestBody EmpDTO empDTO) throws URISyntaxException {
        log.debug("REST request to save Emp : {}", empDTO);
        if (empDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new emp cannot already have an ID")).body(null);
        }
        EmpDTO result = empService.save(empDTO);
        return ResponseEntity.created(new URI("/api/emps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /emps : Updates an existing emp.
     *
     * @param empDTO the empDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated empDTO,
     * or with status 400 (Bad Request) if the empDTO is not valid,
     * or with status 500 (Internal Server Error) if the empDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/emps")
    @Timed
    public ResponseEntity<EmpDTO> updateEmp(@Valid @RequestBody EmpDTO empDTO) throws URISyntaxException {
        log.debug("REST request to update Emp : {}", empDTO);
        if (empDTO.getId() == null) {
            return createEmp(empDTO);
        }
        EmpDTO result = empService.save(empDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, empDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /emps : get all the emps.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of emps in body
     */
    @GetMapping("/emps")
    @Timed
    public List<EmpDTO> getAllEmps() {
        log.debug("REST request to get all Emps");
        return empService.findAll();
    }

    /**
     * GET  /emps/:id : get the "id" emp.
     *
     * @param id the id of the empDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the empDTO, or with status 404 (Not Found)
     */
    @GetMapping("/emps/{id}")
    @Timed
    public ResponseEntity<EmpDTO> getEmp(@PathVariable String id) {
        log.debug("REST request to get Emp : {}", id);
        EmpDTO empDTO = empService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(empDTO));
    }

    /**
     * DELETE  /emps/:id : delete the "id" emp.
     *
     * @param id the id of the empDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/emps/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmp(@PathVariable String id) {
        log.debug("REST request to delete Emp : {}", id);
        empService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
