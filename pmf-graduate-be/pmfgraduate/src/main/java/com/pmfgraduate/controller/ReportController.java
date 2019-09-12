package com.pmfgraduate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pmfgraduate.service.GraduatePaperService;

@RequestMapping("api/reports")
@RestController
public class ReportController {

	@Autowired
	GraduatePaperService graduatePaperService;

	@GetMapping("getAllMentors")
	public ResponseEntity<byte[]> exportMentorsToPDF() throws Exception {
		return ResponseEntity.ok().header("Content-Type", "application/pdf; charset=UTF-8")
				.header("Content-Disposition", "inline; filename=\"mentors.pdf\"")
				.body(graduatePaperService.getAllMentors());
	}

}
