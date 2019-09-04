export class GraduatePaperList {
  graduatePapers: GraduatePaperInfo[];

  constructor() {
    this.graduatePapers = [];
  }
}

interface GraduatePaperInfo {
  id: string;
  author: string;
  mentor: string;
  title: string;
  publicationYear;
  defendedOn;
  pdfFile: string;
}
