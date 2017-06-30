package gk.service;

import gk.service.dto.RitDTO;
import java.util.List;

/**
 * Service Interface for managing Rit.
 */
public interface RitService {

    /**
     * Save a rit.
     *
     * @param ritDTO the entity to save
     * @return the persisted entity
     */
    RitDTO save(RitDTO ritDTO);

    /**
     *  Get all the rits.
     *
     *  @return the list of entities
     */
    List<RitDTO> findAll();

    /**
     *  Get the "id" rit.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RitDTO findOne(String id);

    /**
     *  Delete the "id" rit.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
