package com.barrica.vinotinto.application.mapper;

import com.barrica.vinotinto.infrastructure.adapter.entity.MatchDbo;
import com.barrica.vinotinto.domain.model.dto.MatchDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MatchMapper {

    /*
    @Mappings({
            @Mapping(target = "matchDate", source = "matchTimestamp", qualifiedByName = "convertEpochToDate")
    })*/
    MatchDto mapMatchDboToDto(MatchDbo matchDbo);

/*
    @Named("convertEpochToDate")
    default String epochToLocalDate(String matchDate) {
        long epoch = Long.parseLong(matchDate); // Ejemplo de época (epoch) en segundos
        Instant instant = Instant.ofEpochSecond(epoch);
        LocalDate fechaLegible = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return fechaLegible.format(formatter);
    }*/

    MatchDbo mapMatchDtoToDbo(MatchDto matchDto);
}
