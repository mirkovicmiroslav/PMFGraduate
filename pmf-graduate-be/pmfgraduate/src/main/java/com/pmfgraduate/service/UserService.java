package com.pmfgraduate.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.pmfgraduate.dto.JwtAuthenticationResponse;
import com.pmfgraduate.dto.LoginDTO;
import com.pmfgraduate.dto.RegisterDTO;

public interface UserService {

	public JwtAuthenticationResponse login(LoginDTO userRequest);

	public boolean register(RegisterDTO userRequest);

	public boolean saveFile(MultipartFile file) throws IOException;

}
