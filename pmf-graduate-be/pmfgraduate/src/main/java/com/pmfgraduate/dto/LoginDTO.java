package com.pmfgraduate.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

	@NotBlank(message = "Email is required")
	@Email
	private String email;
	@NotBlank(message = "Password is required")
	private String password;

}
