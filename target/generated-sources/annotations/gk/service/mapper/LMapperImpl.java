package gk.service.mapper;

import gk.domain.L;

import gk.service.dto.LDTO;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Generated;

import org.springframework.stereotype.Component;

@Generated(

    value = "org.mapstruct.ap.MappingProcessor",

    date = "2017-06-30T14:18:32+0530",

    comments = "version: 1.1.0.Final, compiler: javac, environment: Java 1.8.0_131 (Oracle Corporation)"

)

@Component

public class LMapperImpl implements LMapper {

    @Override

    public L toEntity(LDTO dto) {

        if ( dto == null ) {

            return null;
        }

        L l = new L();

        l.setId( dto.getId() );

        l.setLength( dto.getLength() );

        l.setBreadth( dto.getBreadth() );

        l.setArea( dto.getArea() );

        return l;
    }

    @Override

    public LDTO toDto(L entity) {

        if ( entity == null ) {

            return null;
        }

        LDTO lDTO = new LDTO();

        lDTO.setId( entity.getId() );

        lDTO.setLength( entity.getLength() );

        lDTO.setBreadth( entity.getBreadth() );

        lDTO.setArea( entity.getArea() );

        return lDTO;
    }

    @Override

    public List<L> toEntity(List<LDTO> dtoList) {

        if ( dtoList == null ) {

            return null;
        }

        List<L> list = new ArrayList<L>();

        for ( LDTO lDTO : dtoList ) {

            list.add( toEntity( lDTO ) );
        }

        return list;
    }

    @Override

    public List<LDTO> toDto(List<L> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<LDTO> list = new ArrayList<LDTO>();

        for ( L l : entityList ) {

            list.add( toDto( l ) );
        }

        return list;
    }
}

