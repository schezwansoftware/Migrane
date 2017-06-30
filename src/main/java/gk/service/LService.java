package gk.service;

import gk.service.dto.LDTO;
import java.util.List;

/**
 * Service Interface for managing L.
 */
public interface LService {

    /**
     * Save a l.
     *
     * @param lDTO the entity to save
     * @return the persisted entity
     */
    LDTO save(LDTO lDTO);

    /**
     *  Get all the ls.
     *
     *  @return the list of entities
     */
    List<LDTO> findAll();

    /**
     *  Get the "id" l.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    LDTO findOne(String id);

    /**
     *  Delete the "id" l.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
