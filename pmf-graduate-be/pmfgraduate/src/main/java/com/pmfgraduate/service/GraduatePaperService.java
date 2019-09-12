package com.pmfgraduate.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.pmfgraduate.dto.FileResponseDTO;
import com.pmfgraduate.dto.GraduatePaperListDTO;
import com.pmfgraduate.dto.PdfFileDTO;
import com.pmfgraduate.dto.TopMentorsListDTO;
import com.pmfgraduate.model.GraduatePaper;

public interface GraduatePaperService {

	public FileResponseDTO saveFile(MultipartFile file) throws IOException;

	public boolean saveGraduatePaper(GraduatePaper file);

	public GraduatePaperListDTO getAllGraduatePapers();

	public GraduatePaper getByID(String id);

	public PdfFileDTO getGraduatePaperPdf(String id) throws IOException;

	public GraduatePaperListDTO getSearchedFilter(String title, String author, String mentor);

	public TopMentorsListDTO getTopMentors();

	public byte[] getAllMentors();
}
