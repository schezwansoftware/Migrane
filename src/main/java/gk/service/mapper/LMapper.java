package gk.service.mapper;

import gk.domain.*;
import gk.service.dto.LDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity L and its DTO LDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LMapper extends EntityMapper <LDTO, L> {
    
    

}
