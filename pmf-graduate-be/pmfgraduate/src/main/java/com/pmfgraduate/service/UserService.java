package com.pmfgraduate.service;

import com.pmfgraduate.dto.JwtAuthenticationResponseDTO;
import com.pmfgraduate.dto.LoginDTO;
import com.pmfgraduate.dto.RegisterDTO;

public interface UserService {

	public JwtAuthenticationResponseDTO login(LoginDTO userRequest);

	public boolean register(RegisterDTO userRequest);

}
