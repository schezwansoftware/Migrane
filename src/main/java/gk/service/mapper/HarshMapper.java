package gk.service.mapper;

import gk.domain.*;
import gk.service.dto.HarshDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Harsh and its DTO HarshDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HarshMapper extends EntityMapper <HarshDTO, Harsh> {
    
    

}
