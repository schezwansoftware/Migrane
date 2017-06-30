package gk.web.rest;

import com.codahale.metrics.annotation.Timed;
import gk.service.RitService;
import gk.web.rest.util.HeaderUtil;
import gk.service.dto.RitDTO;
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
 * REST controller for managing Rit.
 */
@RestController
@RequestMapping("/api")
public class RitResource {

    private final Logger log = LoggerFactory.getLogger(RitResource.class);

    private static final String ENTITY_NAME = "rit";

    private final RitService ritService;

    public RitResource(RitService ritService) {
        this.ritService = ritService;
    }

    /**
     * POST  /rits : Create a new rit.
     *
     * @param ritDTO the ritDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ritDTO, or with status 400 (Bad Request) if the rit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rits")
    @Timed
    public ResponseEntity<RitDTO> createRit(@RequestBody RitDTO ritDTO) throws URISyntaxException {
        log.debug("REST request to save Rit : {}", ritDTO);
        if (ritDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new rit cannot already have an ID")).body(null);
        }
        RitDTO result = ritService.save(ritDTO);
        return ResponseEntity.created(new URI("/api/rits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rits : Updates an existing rit.
     *
     * @param ritDTO the ritDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ritDTO,
     * or with status 400 (Bad Request) if the ritDTO is not valid,
     * or with status 500 (Internal Server Error) if the ritDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rits")
    @Timed
    public ResponseEntity<RitDTO> updateRit(@RequestBody RitDTO ritDTO) throws URISyntaxException {
        log.debug("REST request to update Rit : {}", ritDTO);
        if (ritDTO.getId() == null) {
            return createRit(ritDTO);
        }
        RitDTO result = ritService.save(ritDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ritDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rits : get all the rits.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rits in body
     */
    @GetMapping("/rits")
    @Timed
    public List<RitDTO> getAllRits() {
        log.debug("REST request to get all Rits");
        return ritService.findAll();
    }

    /**
     * GET  /rits/:id : get the "id" rit.
     *
     * @param id the id of the ritDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ritDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rits/{id}")
    @Timed
    public ResponseEntity<RitDTO> getRit(@PathVariable String id) {
        log.debug("REST request to get Rit : {}", id);
        RitDTO ritDTO = ritService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ritDTO));
    }

    /**
     * DELETE  /rits/:id : delete the "id" rit.
     *
     * @param id the id of the ritDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rits/{id}")
    @Timed
    public ResponseEntity<Void> deleteRit(@PathVariable String id) {
        log.debug("REST request to delete Rit : {}", id);
        ritService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
