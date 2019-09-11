package com.pmfgraduate.mapper;

import com.pmfgraduate.dto.GraduatePaperDTO;
import com.pmfgraduate.model.GraduatePaper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GraduatePaperMapper {

    GraduatePaperDTO map(GraduatePaper source);
}
