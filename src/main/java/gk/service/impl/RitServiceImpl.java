package gk.service.impl;

import gk.service.RitService;
import gk.domain.Rit;
import gk.repository.RitRepository;
import gk.service.dto.RitDTO;
import gk.service.mapper.RitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Rit.
 */
@Service
public class RitServiceImpl implements RitService{

    private final Logger log = LoggerFactory.getLogger(RitServiceImpl.class);

    private final RitRepository ritRepository;

    private final RitMapper ritMapper;

    public RitServiceImpl(RitRepository ritRepository, RitMapper ritMapper) {
        this.ritRepository = ritRepository;
        this.ritMapper = ritMapper;
    }

    /**
     * Save a rit.
     *
     * @param ritDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RitDTO save(RitDTO ritDTO) {
        log.debug("Request to save Rit : {}", ritDTO);
        Rit rit = ritMapper.toEntity(ritDTO);
        rit = ritRepository.save(rit);
        return ritMapper.toDto(rit);
    }

    /**
     *  Get all the rits.
     *
     *  @return the list of entities
     */
    @Override
    public List<RitDTO> findAll() {
        log.debug("Request to get all Rits");
        return ritRepository.findAll().stream()
            .map(ritMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one rit by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public RitDTO findOne(String id) {
        log.debug("Request to get Rit : {}", id);
        Rit rit = ritRepository.findOne(UUID.fromString(id));
        return ritMapper.toDto(rit);
    }

    /**
     *  Delete the  rit by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Rit : {}", id);
        ritRepository.delete(UUID.fromString(id));
    }
}
