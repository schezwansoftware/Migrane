package gk.service.mapper;

import gk.domain.*;
import gk.service.dto.KDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity K and its DTO KDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface KMapper extends EntityMapper <KDTO, K> {
    
    

}
