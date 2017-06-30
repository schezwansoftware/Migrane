package gk.service.impl;

import gk.service.ArService;
import gk.domain.Ar;
import gk.repository.ArRepository;
import gk.service.dto.ArDTO;
import gk.service.mapper.ArMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Ar.
 */
@Service
public class ArServiceImpl implements ArService{

    private final Logger log = LoggerFactory.getLogger(ArServiceImpl.class);

    private final ArRepository arRepository;

    private final ArMapper arMapper;

    public ArServiceImpl(ArRepository arRepository, ArMapper arMapper) {
        this.arRepository = arRepository;
        this.arMapper = arMapper;
    }

    /**
     * Save a ar.
     *
     * @param arDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ArDTO save(ArDTO arDTO) {
        log.debug("Request to save Ar : {}", arDTO);
        Ar ar = arMapper.toEntity(arDTO);
        ar = arRepository.save(ar);
        return arMapper.toDto(ar);
    }

    /**
     *  Get all the ars.
     *
     *  @return the list of entities
     */
    @Override
    public List<ArDTO> findAll() {
        log.debug("Request to get all Ars");
        return arRepository.findAll().stream()
            .map(arMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one ar by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public ArDTO findOne(String id) {
        log.debug("Request to get Ar : {}", id);
        Ar ar = arRepository.findOne(UUID.fromString(id));
        return arMapper.toDto(ar);
    }

    /**
     *  Delete the  ar by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Ar : {}", id);
        arRepository.delete(UUID.fromString(id));
    }
}
