package gk.service;

import gk.service.dto.EmpDTO;
import java.util.List;

/**
 * Service Interface for managing Emp.
 */
public interface EmpService {

    /**
     * Save a emp.
     *
     * @param empDTO the entity to save
     * @return the persisted entity
     */
    EmpDTO save(EmpDTO empDTO);

    /**
     *  Get all the emps.
     *
     *  @return the list of entities
     */
    List<EmpDTO> findAll();

    /**
     *  Get the "id" emp.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EmpDTO findOne(String id);

    /**
     *  Delete the "id" emp.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
