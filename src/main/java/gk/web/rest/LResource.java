package gk.web.rest;

import com.codahale.metrics.annotation.Timed;
import gk.service.LService;
import gk.web.rest.util.HeaderUtil;
import gk.service.dto.LDTO;
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
 * REST controller for managing L.
 */
@RestController
@RequestMapping("/api")
public class LResource {

    private final Logger log = LoggerFactory.getLogger(LResource.class);

    private static final String ENTITY_NAME = "l";

    private final LService lService;

    public LResource(LService lService) {
        this.lService = lService;
    }

    /**
     * POST  /ls : Create a new l.
     *
     * @param lDTO the lDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lDTO, or with status 400 (Bad Request) if the l has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ls")
    @Timed
    public ResponseEntity<LDTO> createL(@Valid @RequestBody LDTO lDTO) throws URISyntaxException {
        log.debug("REST request to save L : {}", lDTO);
        if (lDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new l cannot already have an ID")).body(null);
        }
        LDTO result = lService.save(lDTO);
        return ResponseEntity.created(new URI("/api/ls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ls : Updates an existing l.
     *
     * @param lDTO the lDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lDTO,
     * or with status 400 (Bad Request) if the lDTO is not valid,
     * or with status 500 (Internal Server Error) if the lDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ls")
    @Timed
    public ResponseEntity<LDTO> updateL(@Valid @RequestBody LDTO lDTO) throws URISyntaxException {
        log.debug("REST request to update L : {}", lDTO);
        if (lDTO.getId() == null) {
            return createL(lDTO);
        }
        LDTO result = lService.save(lDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ls : get all the ls.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ls in body
     */
    @GetMapping("/ls")
    @Timed
    public List<LDTO> getAllLS() {
        log.debug("REST request to get all LS");
        return lService.findAll();
    }

    /**
     * GET  /ls/:id : get the "id" l.
     *
     * @param id the id of the lDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ls/{id}")
    @Timed
    public ResponseEntity<LDTO> getL(@PathVariable String id) {
        log.debug("REST request to get L : {}", id);
        LDTO lDTO = lService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(lDTO));
    }

    /**
     * DELETE  /ls/:id : delete the "id" l.
     *
     * @param id the id of the lDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ls/{id}")
    @Timed
    public ResponseEntity<Void> deleteL(@PathVariable String id) {
        log.debug("REST request to delete L : {}", id);
        lService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
