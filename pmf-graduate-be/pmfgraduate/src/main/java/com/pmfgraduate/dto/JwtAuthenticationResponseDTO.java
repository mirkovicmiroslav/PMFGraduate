package com.pmfgraduate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenticationResponseDTO {
	private String accessToken;
	private String tokenType = "Bearer";

	public JwtAuthenticationResponseDTO(String accessToken) {
		this.accessToken = accessToken;
	}
}
