package com.pmfgraduate.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TopMentorsListDTO {

	private List<TopMentorsDTO> mentors;

	public TopMentorsListDTO() {
		this.mentors = new ArrayList<>();
	}

}
