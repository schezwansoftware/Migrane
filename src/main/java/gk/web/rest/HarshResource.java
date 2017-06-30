package gk.web.rest;

import com.codahale.metrics.annotation.Timed;
import gk.service.HarshService;
import gk.web.rest.util.HeaderUtil;
import gk.service.dto.HarshDTO;
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
 * REST controller for managing Harsh.
 */
@RestController
@RequestMapping("/api")
public class HarshResource {

    private final Logger log = LoggerFactory.getLogger(HarshResource.class);

    private static final String ENTITY_NAME = "harsh";

    private final HarshService harshService;

    public HarshResource(HarshService harshService) {
        this.harshService = harshService;
    }

    /**
     * POST  /harshes : Create a new harsh.
     *
     * @param harshDTO the harshDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new harshDTO, or with status 400 (Bad Request) if the harsh has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/harshes")
    @Timed
    public ResponseEntity<HarshDTO> createHarsh(@Valid @RequestBody HarshDTO harshDTO) throws URISyntaxException {
        log.debug("REST request to save Harsh : {}", harshDTO);
        if (harshDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new harsh cannot already have an ID")).body(null);
        }
        HarshDTO result = harshService.save(harshDTO);
        return ResponseEntity.created(new URI("/api/harshes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /harshes : Updates an existing harsh.
     *
     * @param harshDTO the harshDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated harshDTO,
     * or with status 400 (Bad Request) if the harshDTO is not valid,
     * or with status 500 (Internal Server Error) if the harshDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/harshes")
    @Timed
    public ResponseEntity<HarshDTO> updateHarsh(@Valid @RequestBody HarshDTO harshDTO) throws URISyntaxException {
        log.debug("REST request to update Harsh : {}", harshDTO);
        if (harshDTO.getId() == null) {
            return createHarsh(harshDTO);
        }
        HarshDTO result = harshService.save(harshDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, harshDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /harshes : get all the harshes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of harshes in body
     */
    @GetMapping("/harshes")
    @Timed
    public List<HarshDTO> getAllHarshes() {
        log.debug("REST request to get all Harshes");
        return harshService.findAll();
    }

    /**
     * GET  /harshes/:id : get the "id" harsh.
     *
     * @param id the id of the harshDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the harshDTO, or with status 404 (Not Found)
     */
    @GetMapping("/harshes/{id}")
    @Timed
    public ResponseEntity<HarshDTO> getHarsh(@PathVariable String id) {
        log.debug("REST request to get Harsh : {}", id);
        HarshDTO harshDTO = harshService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(harshDTO));
    }

    /**
     * DELETE  /harshes/:id : delete the "id" harsh.
     *
     * @param id the id of the harshDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/harshes/{id}")
    @Timed
    public ResponseEntity<Void> deleteHarsh(@PathVariable String id) {
        log.debug("REST request to delete Harsh : {}", id);
        harshService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
