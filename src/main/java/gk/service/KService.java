package gk.service;

import gk.service.dto.KDTO;
import java.util.List;

/**
 * Service Interface for managing K.
 */
public interface KService {

    /**
     * Save a k.
     *
     * @param kDTO the entity to save
     * @return the persisted entity
     */
    KDTO save(KDTO kDTO);

    /**
     *  Get all the ks.
     *
     *  @return the list of entities
     */
    List<KDTO> findAll();

    /**
     *  Get the "id" k.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    KDTO findOne(String id);

    /**
     *  Delete the "id" k.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
