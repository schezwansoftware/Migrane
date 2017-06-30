package gk.service.mapper;

import gk.service.dto.KDTO;

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

public class KMapperImpl implements KMapper {

    @Override

    public gk.domain.K toEntity(KDTO dto) {

        if ( dto == null ) {

            return null;
        }

        gk.domain.K k = new gk.domain.K();

        k.setId( dto.getId() );

        k.setUsername( dto.getUsername() );

        k.setPassword( dto.getPassword() );

        return k;
    }

    @Override

    public KDTO toDto(gk.domain.K entity) {

        if ( entity == null ) {

            return null;
        }

        KDTO kDTO = new KDTO();

        kDTO.setId( entity.getId() );

        kDTO.setUsername( entity.getUsername() );

        kDTO.setPassword( entity.getPassword() );

        return kDTO;
    }

    @Override

    public List<gk.domain.K> toEntity(List<KDTO> dtoList) {

        if ( dtoList == null ) {

            return null;
        }

        List<gk.domain.K> list = new ArrayList<gk.domain.K>();

        for ( KDTO kDTO : dtoList ) {

            list.add( toEntity( kDTO ) );
        }

        return list;
    }

    @Override

    public List<KDTO> toDto(List<gk.domain.K> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<KDTO> list = new ArrayList<KDTO>();

        for ( gk.domain.K k : entityList ) {

            list.add( toDto( k ) );
        }

        return list;
    }
}

