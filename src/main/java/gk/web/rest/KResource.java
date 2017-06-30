package gk.web.rest;

import com.codahale.metrics.annotation.Timed;
import gk.service.KService;
import gk.web.rest.util.HeaderUtil;
import gk.service.dto.KDTO;
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
 * REST controller for managing K.
 */
@RestController
@RequestMapping("/api")
public class KResource {

    private final Logger log = LoggerFactory.getLogger(KResource.class);

    private static final String ENTITY_NAME = "k";

    private final KService kService;

    public KResource(KService kService) {
        this.kService = kService;
    }

    /**
     * POST  /ks : Create a new k.
     *
     * @param kDTO the kDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new kDTO, or with status 400 (Bad Request) if the k has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ks")
    @Timed
    public ResponseEntity<KDTO> createK(@Valid @RequestBody KDTO kDTO) throws URISyntaxException {
        log.debug("REST request to save K : {}", kDTO);
        if (kDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new k cannot already have an ID")).body(null);
        }
        KDTO result = kService.save(kDTO);
        return ResponseEntity.created(new URI("/api/ks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ks : Updates an existing k.
     *
     * @param kDTO the kDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated kDTO,
     * or with status 400 (Bad Request) if the kDTO is not valid,
     * or with status 500 (Internal Server Error) if the kDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ks")
    @Timed
    public ResponseEntity<KDTO> updateK(@Valid @RequestBody KDTO kDTO) throws URISyntaxException {
        log.debug("REST request to update K : {}", kDTO);
        if (kDTO.getId() == null) {
            return createK(kDTO);
        }
        KDTO result = kService.save(kDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, kDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ks : get all the ks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ks in body
     */
    @GetMapping("/ks")
    @Timed
    public List<KDTO> getAllKS() {
        log.debug("REST request to get all KS");
        return kService.findAll();
    }

    /**
     * GET  /ks/:id : get the "id" k.
     *
     * @param id the id of the kDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the kDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ks/{id}")
    @Timed
    public ResponseEntity<KDTO> getK(@PathVariable String id) {
        log.debug("REST request to get K : {}", id);
        KDTO kDTO = kService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(kDTO));
    }

    /**
     * DELETE  /ks/:id : delete the "id" k.
     *
     * @param id the id of the kDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ks/{id}")
    @Timed
    public ResponseEntity<Void> deleteK(@PathVariable String id) {
        log.debug("REST request to delete K : {}", id);
        kService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
