package gk.service;

import gk.service.dto.StudentDTO;
import java.util.List;

/**
 * Service Interface for managing Student.
 */
public interface StudentService {

    /**
     * Save a student.
     *
     * @param studentDTO the entity to save
     * @return the persisted entity
     */
    StudentDTO save(StudentDTO studentDTO);

    /**
     *  Get all the students.
     *
     *  @return the list of entities
     */
    List<StudentDTO> findAll();

    /**
     *  Get the "id" student.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    StudentDTO findOne(String id);

    /**
     *  Delete the "id" student.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
