package com.pmfgraduate.service;

import com.pmfgraduate.dto.FileResponseDTO;
import com.pmfgraduate.dto.GraduatePaperListDTO;
import com.pmfgraduate.dto.PdfFileDTO;
import com.pmfgraduate.model.GraduatePaper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface GraduatePaperService {

    public FileResponseDTO saveFile(MultipartFile file) throws IOException;
    public boolean saveGraduatePaper(GraduatePaper file);
    public GraduatePaperListDTO getAllGraduatePapers();
    public GraduatePaper getByID(String id);
    public PdfFileDTO getGraduatePaperPdf(String id) throws IOException;
}
