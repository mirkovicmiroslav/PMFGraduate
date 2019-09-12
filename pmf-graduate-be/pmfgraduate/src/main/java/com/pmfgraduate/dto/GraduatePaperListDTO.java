package com.pmfgraduate.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GraduatePaperListDTO {

	private List<GraduatePaperDTO> graduatePapers;

	public GraduatePaperListDTO() {
		this.graduatePapers = new ArrayList<>();
	}
}
