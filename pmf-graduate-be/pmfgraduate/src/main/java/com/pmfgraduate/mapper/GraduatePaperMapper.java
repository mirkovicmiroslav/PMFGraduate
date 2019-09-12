package com.pmfgraduate.mapper;

import org.mapstruct.Mapper;

import com.pmfgraduate.dto.GraduatePaperDTO;
import com.pmfgraduate.model.GraduatePaper;

@Mapper(componentModel = "spring")
public interface GraduatePaperMapper {

	GraduatePaperDTO map(GraduatePaper source);
}
