package com.pmfgraduate.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "graduate-paper")
public class File {

    @Id
    private String id;
    private int accessionNumber;
    private int identificationNumber;
    private String documentType;
    private String typeOfRecord;
    private String contentCode;
    private String author;
    private String mentor;
    private String title;
    private String languageOfText;
    private String languageOfAbstract;
    private String countryOfPublication;
    private String localityOfPublication;
    private int publicationYear;
    private String publisher;
    private String publicationPlace;
    private String physicalDescription;
    private String scientificField;
    private String scientificDiscipline;
    private String keyWords;
    private String holdingData;
    private String note;
    private String excerpt;
    private Date acceptedByScientificBoard;
    private Date defendedOn;
    private String graduatePaperAbstract;
    private String president;
    private String memberFirst;
    private String memberSecond;
    private String pdfFile;
}
