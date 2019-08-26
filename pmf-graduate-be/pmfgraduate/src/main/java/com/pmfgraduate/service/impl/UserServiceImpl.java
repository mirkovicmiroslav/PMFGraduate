package com.pmfgraduate.service.impl;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.pmfgraduate.dto.JwtAuthenticationResponse;
import com.pmfgraduate.dto.LoginDTO;
import com.pmfgraduate.dto.RegisterDTO;
import com.pmfgraduate.exception.PmfGraduateException;
import com.pmfgraduate.model.User;
import com.pmfgraduate.repository.UserRepository;
import com.pmfgraduate.security.auth.AuthenticatedUser;
import com.pmfgraduate.security.auth.JwtTokenProvider;
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
	UserDetailsService userDetailsService;
	@Autowired
	JwtTokenProvider jwtProvired;
	@Autowired
	AuthenticatedUser authentication;
	@Autowired
	GridFsOperations gridFsOperations;

	@Override
	public JwtAuthenticationResponse login(LoginDTO userRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);

			UserDetails userDetails = userDetailsService.loadUserByUsername(userRequest.getEmail());

			String jwt = jwtProvired.createToken(userDetails);

			return new JwtAuthenticationResponse(jwt);
		} catch (BadCredentialsException e) {
			throw new PmfGraduateException(HttpStatus.BAD_REQUEST, "Incorrect password!");
		}
	}

	@Override
	public boolean register(RegisterDTO userRequest) {
		if (userRepository.findByEmail(userRequest.getEmail()) != null) {
			throw new PmfGraduateException(HttpStatus.BAD_REQUEST, "Email already exist");
		} else {
			User user = new User();
			user.setFirstName(userRequest.getFirstName());
			user.setLastName(userRequest.getLastName());
			user.setEmail(userRequest.getEmail());
			user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
			user.setRole("USER");

			userRepository.save(user);

			return true;
		}
	}

	@Override
	public boolean saveFile(MultipartFile file) throws IOException {
		try {
			DBObject metaData = new BasicDBObject();
			metaData.put("organization", "DMI");

			InputStream inputStream = file.getInputStream();
			metaData.put("type", file.getContentType());

			gridFsOperations.store(inputStream, file.getOriginalFilename(), file.getContentType(), metaData);

			return true;
		} catch (MultipartException e) {
			throw new PmfGraduateException(HttpStatus.BAD_REQUEST, "Some problem...");
		}
	}

}
