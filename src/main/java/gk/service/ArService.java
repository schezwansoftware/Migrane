package gk.service;

import gk.service.dto.ArDTO;
import java.util.List;

/**
 * Service Interface for managing Ar.
 */
public interface ArService {

    /**
     * Save a ar.
     *
     * @param arDTO the entity to save
     * @return the persisted entity
     */
    ArDTO save(ArDTO arDTO);

    /**
     *  Get all the ars.
     *
     *  @return the list of entities
     */
    List<ArDTO> findAll();

    /**
     *  Get the "id" ar.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ArDTO findOne(String id);

    /**
     *  Delete the "id" ar.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
