package gk.service.impl;

import gk.service.LService;
import gk.domain.L;
import gk.repository.LRepository;
import gk.service.dto.LDTO;
import gk.service.mapper.LMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing L.
 */
@Service
public class LServiceImpl implements LService{

    private final Logger log = LoggerFactory.getLogger(LServiceImpl.class);

    private final LRepository lRepository;

    private final LMapper lMapper;

    public LServiceImpl(LRepository lRepository, LMapper lMapper) {
        this.lRepository = lRepository;
        this.lMapper = lMapper;
    }

    /**
     * Save a l.
     *
     * @param lDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LDTO save(LDTO lDTO) {
        log.debug("Request to save L : {}", lDTO);
        L l = lMapper.toEntity(lDTO);
        l = lRepository.save(l);
        return lMapper.toDto(l);
    }

    /**
     *  Get all the ls.
     *
     *  @return the list of entities
     */
    @Override
    public List<LDTO> findAll() {
        log.debug("Request to get all LS");
        return lRepository.findAll().stream()
            .map(lMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one l by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public LDTO findOne(String id) {
        log.debug("Request to get L : {}", id);
        L l = lRepository.findOne(UUID.fromString(id));
        return lMapper.toDto(l);
    }

    /**
     *  Delete the  l by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete L : {}", id);
        lRepository.delete(UUID.fromString(id));
    }
}
