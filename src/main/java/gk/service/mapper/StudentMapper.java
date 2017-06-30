package gk.service.mapper;

import gk.domain.*;
import gk.service.dto.StudentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Student and its DTO StudentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StudentMapper extends EntityMapper <StudentDTO, Student> {
    
    

}
