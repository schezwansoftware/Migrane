package gk.service.impl;

import gk.service.StudentService;
import gk.domain.Student;
import gk.repository.StudentRepository;
import gk.service.dto.StudentDTO;
import gk.service.mapper.StudentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Student.
 */
@Service
public class StudentServiceImpl implements StudentService{

    private final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    /**
     * Save a student.
     *
     * @param studentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StudentDTO save(StudentDTO studentDTO) {
        log.debug("Request to save Student : {}", studentDTO);
        Student student = studentMapper.toEntity(studentDTO);
        student = studentRepository.save(student);
        return studentMapper.toDto(student);
    }

    /**
     *  Get all the students.
     *
     *  @return the list of entities
     */
    @Override
    public List<StudentDTO> findAll() {
        log.debug("Request to get all Students");
        return studentRepository.findAll().stream()
            .map(studentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one student by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public StudentDTO findOne(String id) {
        log.debug("Request to get Student : {}", id);
        Student student = studentRepository.findOne(UUID.fromString(id));
        return studentMapper.toDto(student);
    }

    /**
     *  Delete the  student by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Student : {}", id);
        studentRepository.delete(UUID.fromString(id));
    }
}
