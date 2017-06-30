package gk.service.mapper;

import gk.domain.*;
import gk.service.dto.ArDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Ar and its DTO ArDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ArMapper extends EntityMapper <ArDTO, Ar> {
    
    

}
