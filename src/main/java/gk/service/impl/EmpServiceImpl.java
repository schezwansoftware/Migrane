package gk.service.impl;

import gk.service.EmpService;
import gk.domain.Emp;
import gk.repository.EmpRepository;
import gk.service.dto.EmpDTO;
import gk.service.mapper.EmpMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Emp.
 */
@Service
public class EmpServiceImpl implements EmpService{

    private final Logger log = LoggerFactory.getLogger(EmpServiceImpl.class);

    private final EmpRepository empRepository;

    private final EmpMapper empMapper;

    public EmpServiceImpl(EmpRepository empRepository, EmpMapper empMapper) {
        this.empRepository = empRepository;
        this.empMapper = empMapper;
    }

    /**
     * Save a emp.
     *
     * @param empDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EmpDTO save(EmpDTO empDTO) {
        log.debug("Request to save Emp : {}", empDTO);
        Emp emp = empMapper.toEntity(empDTO);
        emp = empRepository.save(emp);
        return empMapper.toDto(emp);
    }

    /**
     *  Get all the emps.
     *
     *  @return the list of entities
     */
    @Override
    public List<EmpDTO> findAll() {
        log.debug("Request to get all Emps");
        return empRepository.findAll().stream()
            .map(empMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one emp by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public EmpDTO findOne(String id) {
        log.debug("Request to get Emp : {}", id);
        Emp emp = empRepository.findOne(UUID.fromString(id));
        return empMapper.toDto(emp);
    }

    /**
     *  Delete the  emp by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Emp : {}", id);
        empRepository.delete(UUID.fromString(id));
    }
}
