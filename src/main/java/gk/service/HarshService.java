package gk.service;

import gk.service.dto.HarshDTO;
import java.util.List;

/**
 * Service Interface for managing Harsh.
 */
public interface HarshService {

    /**
     * Save a harsh.
     *
     * @param harshDTO the entity to save
     * @return the persisted entity
     */
    HarshDTO save(HarshDTO harshDTO);

    /**
     *  Get all the harshes.
     *
     *  @return the list of entities
     */
    List<HarshDTO> findAll();

    /**
     *  Get the "id" harsh.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    HarshDTO findOne(String id);

    /**
     *  Delete the "id" harsh.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
