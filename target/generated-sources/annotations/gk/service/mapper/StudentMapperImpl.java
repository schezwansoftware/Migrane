package gk.service.mapper;

import gk.domain.Student;

import gk.service.dto.StudentDTO;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Generated;

import org.springframework.stereotype.Component;

@Generated(

    value = "org.mapstruct.ap.MappingProcessor",

    date = "2017-06-30T14:18:31+0530",

    comments = "version: 1.1.0.Final, compiler: javac, environment: Java 1.8.0_131 (Oracle Corporation)"

)

@Component

public class StudentMapperImpl implements StudentMapper {

    @Override

    public Student toEntity(StudentDTO dto) {

        if ( dto == null ) {

            return null;
        }

        Student student = new Student();

        student.setId( dto.getId() );

        student.setName( dto.getName() );

        student.setAge( dto.getAge() );

        return student;
    }

    @Override

    public StudentDTO toDto(Student entity) {

        if ( entity == null ) {

            return null;
        }

        StudentDTO studentDTO = new StudentDTO();

        studentDTO.setId( entity.getId() );

        studentDTO.setName( entity.getName() );

        studentDTO.setAge( entity.getAge() );

        return studentDTO;
    }

    @Override

    public List<Student> toEntity(List<StudentDTO> dtoList) {

        if ( dtoList == null ) {

            return null;
        }

        List<Student> list = new ArrayList<Student>();

        for ( StudentDTO studentDTO : dtoList ) {

            list.add( toEntity( studentDTO ) );
        }

        return list;
    }

    @Override

    public List<StudentDTO> toDto(List<Student> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<StudentDTO> list = new ArrayList<StudentDTO>();

        for ( Student student : entityList ) {

            list.add( toDto( student ) );
        }

        return list;
    }
}

