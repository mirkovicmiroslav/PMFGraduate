package com.pmfgraduate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MentorReportDTO {

	private String mentor;
	private int mentorCount;
	private int memberOfBoard;

}
