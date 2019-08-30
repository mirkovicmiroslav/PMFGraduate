package com.pmfgraduate.mapper;

import com.pmfgraduate.dto.GraduatePaperDTO;
import com.pmfgraduate.model.File;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileMapper {

    File map(GraduatePaperDTO source);
}
