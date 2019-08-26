package com.pmfgraduate.controller;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pmfgraduate.dto.LoginDTO;
import com.pmfgraduate.dto.RegisterDTO;
import com.pmfgraduate.service.UserService;

@RequestMapping("api")
@RestController
@PermitAll
public class AuthController {

	@Autowired
	UserService userService;

	@PostMapping("login")
	public ResponseEntity<?> login(@RequestBody LoginDTO userRequest) {
		return ResponseEntity.ok(userService.login(userRequest));
	}

	@PostMapping("register")
	public ResponseEntity<?> register(@RequestBody RegisterDTO userRequest) {
		return ResponseEntity.ok(userService.register(userRequest));
	}

}