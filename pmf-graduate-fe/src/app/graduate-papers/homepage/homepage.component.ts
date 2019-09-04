import { GraduatePaper } from "./../../core/models/graduatePaper.model";
import { PdfViewerComponent } from "ng2-pdf-viewer";
import { GraduatePaperService } from "../../core/services/graduatePaper.service";
import { GraduatePaperList } from "../../core/models/graduatePaperList.model";
import { Component, OnInit, ViewChild, OnDestroy } from "@angular/core";
import { Subscription } from "rxjs";

@Component({
  selector: "app-homepage",
  templateUrl: "./homepage.component.html",
  styleUrls: ["./homepage.component.css"]
})
export class HomepageComponent implements OnInit, OnDestroy {
  private subscription: Subscription;
  graduatePaperList: GraduatePaperList = new GraduatePaperList();
  @ViewChild(PdfViewerComponent, { static: false })
  public pdfComponent: PdfViewerComponent;
  graduatePaper: GraduatePaper = new GraduatePaper();
  pdfSrc: any;
  page: number = 1;
  totalPages: number;
  pdfQuery = "";
  pdf: any;

  constructor(private graduatePaperService: GraduatePaperService) {}

  ngOnInit() {
    this.getAllGraduatePapers();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  getAllGraduatePapers() {
    this.subscription = this.graduatePaperService
      .getAllGraduatePapers()
      .subscribe(response => {
        this.graduatePaperList.graduatePapers = response.graduatePapers;
      });
  }

  onOpenGraduatePaper(id: string) {
    this.graduatePaperService.getPdfFileById(id).subscribe(response => {
      this.pdfSrc = this.base64ToArrayBuffer(response.pdfBytes);
    });
  }

  base64ToArrayBuffer(base64) {
    let binary_string = window.atob(base64);
    let len = binary_string.length;
    let bytes = new Uint8Array(len);
    for (let i = 0; i < len; i++) {
      bytes[i] = binary_string.charCodeAt(i);
    }
    return bytes.buffer;
  }

  searchQueryChanged(newQuery: string) {
    if (newQuery !== this.pdfQuery) {
      this.pdfQuery = newQuery;
      this.pdfComponent.pdfFindController.executeCommand("find", {
        query: this.pdfQuery,
        caseSensitive: false,
        findPrevious: undefined,
        phraseSearch: true,
        highlightAll: true
      });
    } else {
      this.pdfComponent.pdfFindController.executeCommand("findagain", {
        query: this.pdfQuery,
        caseSensitive: false,
        findPrevious: undefined,
        phraseSearch: true,
        highlightAll: true
      });
    }
  }

  afterLoadComplete(pdf: any) {
    this.totalPages = pdf.numPages;
    this.pdf = pdf;
  }

  nextPage() {
    this.page++;
  }

  prevPage() {
    this.page--;
  }
}
