package gk.service.impl;

import gk.service.KService;
import gk.domain.K;
import gk.repository.KRepository;
import gk.service.dto.KDTO;
import gk.service.mapper.KMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing K.
 */
@Service
public class KServiceImpl implements KService{

    private final Logger log = LoggerFactory.getLogger(KServiceImpl.class);

    private final KRepository kRepository;

    private final KMapper kMapper;

    public KServiceImpl(KRepository kRepository, KMapper kMapper) {
        this.kRepository = kRepository;
        this.kMapper = kMapper;
    }

    /**
     * Save a k.
     *
     * @param kDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public KDTO save(KDTO kDTO) {
        log.debug("Request to save K : {}", kDTO);
        K k = kMapper.toEntity(kDTO);
        k = kRepository.save(k);
        return kMapper.toDto(k);
    }

    /**
     *  Get all the ks.
     *
     *  @return the list of entities
     */
    @Override
    public List<KDTO> findAll() {
        log.debug("Request to get all KS");
        return kRepository.findAll().stream()
            .map(kMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one k by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public KDTO findOne(String id) {
        log.debug("Request to get K : {}", id);
        K k = kRepository.findOne(UUID.fromString(id));
        return kMapper.toDto(k);
    }

    /**
     *  Delete the  k by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete K : {}", id);
        kRepository.delete(UUID.fromString(id));
    }
}
