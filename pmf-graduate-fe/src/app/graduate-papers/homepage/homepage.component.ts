import { GraduatePaper } from "./../../core/models/graduatePaper.model";
import { PdfViewerComponent } from "ng2-pdf-viewer";
import { GraduatePaperService } from "../../core/services/graduatePaper.service";
import { GraduatePaperList } from "../../core/models/graduatePaperList.model";
import {
  Component,
  OnInit,
  ViewChild,
  OnDestroy,
  ElementRef
} from "@angular/core";
import { debounceTime, map, distinctUntilChanged } from "rxjs/operators";
import { fromEvent, Subscription } from "rxjs";

@Component({
  selector: "app-homepage",
  templateUrl: "./homepage.component.html",
  styleUrls: ["./homepage.component.css"]
})
export class HomepageComponent implements OnInit, OnDestroy {
  private subscription: Subscription = new Subscription();
  graduatePaperList: GraduatePaperList = new GraduatePaperList();
  @ViewChild(PdfViewerComponent, { static: false })
  public pdfComponent: PdfViewerComponent;
  graduatePaper: GraduatePaper = new GraduatePaper();
  pdfSrc: any;
  page: number = 1;
  totalPages: number;
  pdfQuery = "";
  pdf: any;
  @ViewChild("titleSearchInput", { static: true })
  titleSearchInput: ElementRef;
  @ViewChild("authorSearchInput", { static: true })
  authorSearchInput: ElementRef;
  @ViewChild("mentorSearchInput", { static: true })
  mentorSearchInput: ElementRef;
  titleSearchString: string = "";
  authorSearchString: string = "";
  mentorSearchString: string = "";

  constructor(private graduatePaperService: GraduatePaperService) {}

  ngOnInit() {
    this.subscription.add(this.getAllGraduatePapers());
    this.onSearch(this.titleSearchInput);
    this.onSearch(this.authorSearchInput);
    this.onSearch(this.mentorSearchInput);
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  onSearch(searchInput: ElementRef) {
    this.subscription.add(
      fromEvent(searchInput.nativeElement, "keyup")
        .pipe(
          map((event: any) => {
            return event.target.value;
          }),
          debounceTime(200),
          distinctUntilChanged()
        )
        .subscribe((text: string) => {
          if (searchInput == this.titleSearchInput) {
            this.titleSearchString = text;
          } else if (searchInput == this.authorSearchInput) {
            this.authorSearchString = text;
          } else {
            this.mentorSearchString = text;
          }
          this.getSearchedFilter();
        })
    );
  }

  getAllGraduatePapers() {
    this.graduatePaperService.getAllGraduatePapers().subscribe(response => {
      this.graduatePaperList.graduatePapers = response.graduatePapers;
    });
  }

  getSearchedFilter() {
    this.graduatePaperService
      .getSearchedFilter(
        this.titleSearchString,
        this.authorSearchString,
        this.mentorSearchString
      )
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

  onKeyPress(event: KeyboardEvent) {
    if (event.keyCode == 37) {
      this.page--;
    }
    if (event.keyCode == 39) {
      this.page++;
    }
  }

  nextPage() {
    this.page++;
  }

  prevPage() {
    this.page--;
  }
}
