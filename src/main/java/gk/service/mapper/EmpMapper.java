package gk.service.mapper;

import gk.domain.*;
import gk.service.dto.EmpDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Emp and its DTO EmpDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmpMapper extends EntityMapper <EmpDTO, Emp> {
    
    

}
