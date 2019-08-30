package com.pmfgraduate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GraduatePaperDTO {

    private String id;
    private String author;
    private String mentor;
    private String title;
    private int publicationYear;
    private Date defendedOn;
}
