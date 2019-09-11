package com.pmfgraduate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TopMentorsListDTO {

    private List<TopMentorsDTO> mentors;

    public TopMentorsListDTO(){
        this.mentors = new ArrayList<>();
    }

}
