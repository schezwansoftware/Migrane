package gk.web.rest;

import com.codahale.metrics.annotation.Timed;
import gk.service.ArService;
import gk.web.rest.util.HeaderUtil;
import gk.service.dto.ArDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST controller for managing Ar.
 */
@RestController
@RequestMapping("/api")
public class ArResource {

    private final Logger log = LoggerFactory.getLogger(ArResource.class);

    private static final String ENTITY_NAME = "ar";

    private final ArService arService;

    public ArResource(ArService arService) {
        this.arService = arService;
    }

    /**
     * POST  /ars : Create a new ar.
     *
     * @param arDTO the arDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new arDTO, or with status 400 (Bad Request) if the ar has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ars")
    @Timed
    public ResponseEntity<ArDTO> createAr(@RequestBody ArDTO arDTO) throws URISyntaxException {
        log.debug("REST request to save Ar : {}", arDTO);
        if (arDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ar cannot already have an ID")).body(null);
        }
        ArDTO result = arService.save(arDTO);
        return ResponseEntity.created(new URI("/api/ars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ars : Updates an existing ar.
     *
     * @param arDTO the arDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated arDTO,
     * or with status 400 (Bad Request) if the arDTO is not valid,
     * or with status 500 (Internal Server Error) if the arDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ars")
    @Timed
    public ResponseEntity<ArDTO> updateAr(@RequestBody ArDTO arDTO) throws URISyntaxException {
        log.debug("REST request to update Ar : {}", arDTO);
        if (arDTO.getId() == null) {
            return createAr(arDTO);
        }
        ArDTO result = arService.save(arDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, arDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ars : get all the ars.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ars in body
     */
    @GetMapping("/ars")
    @Timed
    public List<ArDTO> getAllArs() {
        log.debug("REST request to get all Ars");
        return arService.findAll();
    }

    /**
     * GET  /ars/:id : get the "id" ar.
     *
     * @param id the id of the arDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the arDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ars/{id}")
    @Timed
    public ResponseEntity<ArDTO> getAr(@PathVariable String id) {
        log.debug("REST request to get Ar : {}", id);
        ArDTO arDTO = arService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(arDTO));
    }

    /**
     * DELETE  /ars/:id : delete the "id" ar.
     *
     * @param id the id of the arDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ars/{id}")
    @Timed
    public ResponseEntity<Void> deleteAr(@PathVariable String id) {
        log.debug("REST request to delete Ar : {}", id);
        arService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
