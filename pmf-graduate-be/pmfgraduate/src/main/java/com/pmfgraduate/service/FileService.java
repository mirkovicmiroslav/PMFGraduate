package com.pmfgraduate.service;

import com.pmfgraduate.dto.FileResponseDTO;
import com.pmfgraduate.dto.GraduatePaperDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    public FileResponseDTO saveFile(MultipartFile file) throws IOException;
    public boolean saveGraduatePaper(GraduatePaperDTO graduatePaperDTO);
}
