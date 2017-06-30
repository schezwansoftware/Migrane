package gk.service.impl;

import gk.service.HarshService;
import gk.domain.Harsh;
import gk.repository.HarshRepository;
import gk.service.dto.HarshDTO;
import gk.service.mapper.HarshMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Harsh.
 */
@Service
public class HarshServiceImpl implements HarshService{

    private final Logger log = LoggerFactory.getLogger(HarshServiceImpl.class);

    private final HarshRepository harshRepository;

    private final HarshMapper harshMapper;

    public HarshServiceImpl(HarshRepository harshRepository, HarshMapper harshMapper) {
        this.harshRepository = harshRepository;
        this.harshMapper = harshMapper;
    }

    /**
     * Save a harsh.
     *
     * @param harshDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public HarshDTO save(HarshDTO harshDTO) {
        log.debug("Request to save Harsh : {}", harshDTO);
        Harsh harsh = harshMapper.toEntity(harshDTO);
        harsh = harshRepository.save(harsh);
        return harshMapper.toDto(harsh);
    }

    /**
     *  Get all the harshes.
     *
     *  @return the list of entities
     */
    @Override
    public List<HarshDTO> findAll() {
        log.debug("Request to get all Harshes");
        return harshRepository.findAll().stream()
            .map(harshMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one harsh by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public HarshDTO findOne(String id) {
        log.debug("Request to get Harsh : {}", id);
        Harsh harsh = harshRepository.findOne(UUID.fromString(id));
        return harshMapper.toDto(harsh);
    }

    /**
     *  Delete the  harsh by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Harsh : {}", id);
        harshRepository.delete(UUID.fromString(id));
    }
}
