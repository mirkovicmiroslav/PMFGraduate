package com.pmfgraduate.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pmfgraduate.dto.JwtAuthenticationResponseDTO;
import com.pmfgraduate.dto.LoginDTO;
import com.pmfgraduate.dto.RegisterDTO;
import com.pmfgraduate.exception.PmfGraduateException;
import com.pmfgraduate.mapper.UserMapper;
import com.pmfgraduate.model.User;
import com.pmfgraduate.repository.UserRepository;
import com.pmfgraduate.security.JwtTokenProvider;
import com.pmfgraduate.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	@Autowired
	UserMapper userMapper;

	@Override
	public JwtAuthenticationResponseDTO login(LoginDTO userRequest) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword()));
			UserDetails userDetails = customUserDetailsService.loadUserByUsername(userRequest.getEmail());

			String jwt = jwtTokenProvider.createToken(userDetails);

			return new JwtAuthenticationResponseDTO(jwt);
		} catch (BadCredentialsException e) {
			throw new PmfGraduateException(HttpStatus.BAD_REQUEST, "Neispravna e-mail adresa ili lozinka.");
		}
	}

	@Override
	public boolean register(RegisterDTO userRequest) {
		if (userRepository.findByEmail(userRequest.getEmail()) != null) {
			throw new PmfGraduateException(HttpStatus.BAD_REQUEST, "E-mail već postoji.");
		} else {
			User user = userMapper.map(userRequest);
			user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
			user.setRole("USER");

			user = userRepository.save(user);

			if (user != null) {
				return true;
			} else {
				throw new PmfGraduateException(HttpStatus.INTERNAL_SERVER_ERROR, "Neuspešna registracija.");
			}
		}
	}

}
