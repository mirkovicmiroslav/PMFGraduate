export class GraduatePaper {
  accessionNumber?: number;
  identificationNumber?: number;
  documentType: string;
  typeOfRecord: string;
  contentCode: string;
  author: string;
  mentor: string;
  title: string;
  languageOfText: string;
  languageOfAbstract: string;
  countryOfPublication: string;
  localityOfPublication: string;
  publicationYear: number;
  publisher: string;
  publicationPlace: string;
  physicalDescription: string;
  scientificField: string;
  scientificDiscipline: string;
  keyWords: string;
  holdingData: string;
  note: string;
  excerpt: string;
  acceptedByScientificBoard: Date;
  defendedOn: Date;
  president: string;
  memberFirst: string;
  memberSecond: string;
  pdfFile: string;

  constructor() {
    this.accessionNumber;
    this.identificationNumber;
    this.documentType = "Monografska dokumentacija";
    this.typeOfRecord = "Tekstualni štampani materijal";
    this.contentCode = "Diplomski rad";
    this.author = "";
    this.mentor = "";
    this.title = "";
    this.languageOfText = "srpski (latinica)";
    this.languageOfAbstract = "srpski/engleski";
    this.countryOfPublication = "Srbija";
    this.localityOfPublication = "Vojvodina";
    this.publicationYear;
    this.publisher = "Autorski reprint";
    this.publicationPlace =
      "Prirodno-matematički fakultet, Trg Dositeja Obradovića 4, Novi Sad";
    this.physicalDescription = "";
    this.scientificField = "";
    this.scientificDiscipline = "";
    this.keyWords = "";
    this.holdingData =
      "Biblioteka departmana za matematiku i informatiku, PMF-a u Novom Sadu";
    this.note = "";
    this.excerpt = "";
    this.acceptedByScientificBoard;
    this.defendedOn;
    this.president = "";
    this.memberFirst = "";
    this.memberSecond = "";
    this.pdfFile = "";
  }
}
