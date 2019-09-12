package com.pmfgraduate.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pmfgraduate.model.GraduatePaper;
import com.pmfgraduate.service.GraduatePaperService;

@RequestMapping("api/admin")
@RestController
public class AdminController {

	@Autowired
	GraduatePaperService fileService;

	@PostMapping("/uploadFile")
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	public ResponseEntity<?> saveFile(@RequestBody MultipartFile file) throws IOException {
		return ResponseEntity.ok(fileService.saveFile(file));
	}

	@PostMapping("/saveGraduatePaper")
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	public ResponseEntity<?> saveGraduatePaperInfo(@Valid @RequestBody GraduatePaper graduatePaper) {
		return ResponseEntity.ok(fileService.saveGraduatePaper(graduatePaper));
	}

}
