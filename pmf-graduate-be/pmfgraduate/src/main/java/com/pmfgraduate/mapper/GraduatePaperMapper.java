package com.pmfgraduate.mapper;

import com.pmfgraduate.dto.GraduatePaperDTO;
import com.pmfgraduate.model.GraduatePaper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GraduatePaperMapper {

    GraduatePaper map(GraduatePaperDTO source);

    @Mapping(target = "id", source = "source.id")
    GraduatePaperDTO map(GraduatePaper source);
}
