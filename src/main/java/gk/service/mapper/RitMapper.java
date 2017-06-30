package gk.service.mapper;

import gk.domain.*;
import gk.service.dto.RitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Rit and its DTO RitDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RitMapper extends EntityMapper <RitDTO, Rit> {
    
    

}
