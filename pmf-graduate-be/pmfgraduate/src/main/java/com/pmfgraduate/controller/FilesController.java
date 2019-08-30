package com.pmfgraduate.controller;

import com.pmfgraduate.dto.GraduatePaperDTO;
import com.pmfgraduate.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("api/admin")
@RestController
public class FilesController {

	@Autowired
	FileService fileService;

	@PostMapping("/uploadFile")
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	public ResponseEntity<?> saveFile(@RequestBody MultipartFile file) throws IOException {
		return ResponseEntity.ok(fileService.saveFile(file));
	}

	@PostMapping("/saveGraduatePaper")
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	public ResponseEntity<?> saveGraduatePaperInfo(@RequestBody GraduatePaperDTO graduatePaperDTO) {
		return ResponseEntity.ok(fileService.saveGraduatePaper(graduatePaperDTO));
	}


}
