package com.pmfgraduate.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pmfgraduate.service.UserService;

@RequestMapping("api/admin/")
@RestController
public class FilesController {

	@Autowired
	UserService userService;

	@PostMapping("saveFile")
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	public ResponseEntity<?> saveFile(@RequestBody MultipartFile file) throws IOException {
		return ResponseEntity.ok(userService.saveFile(file));
	}
}
