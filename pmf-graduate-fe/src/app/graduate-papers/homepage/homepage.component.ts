import { GraduatePaperInfo } from "./../../core/models/graduatePaperInfo.model";
import { TopMentorsList } from "./../../core/models/topMentorsList.model";
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
import { AuthenticationService } from "src/app/core/services/authentication.service";
import { ToastrService } from "ngx-toastr";

@Component({
  selector: "app-homepage",
  templateUrl: "./homepage.component.html",
  styleUrls: ["./homepage.component.css"]
})
export class HomepageComponent implements OnInit, OnDestroy {
  private subscription: Subscription = new Subscription();
  graduatePaperList: GraduatePaperList = new GraduatePaperList();
  graduatePaperFavourites: GraduatePaperInfo[] = [];
  topMentorsList: TopMentorsList = new TopMentorsList();
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
  activeAddToFavourites: Number[] = [];

  constructor(
    private graduatePaperService: GraduatePaperService,
    public authService: AuthenticationService,
    private toastr: ToastrService
  ) {}

  ngOnInit() {
    this.subscription.add(this.getAllGraduatePapers());
    this.subscription.add(this.getTopMentors());
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
      for (let graduatePaper of response.graduatePapers) {
        this.activeAddToFavourites.push(graduatePaper.id);
      }
      this.getBooksInCart();
    });
  }

  getTopMentors() {
    this.graduatePaperService.getTopMentors().subscribe(response => {
      this.topMentorsList.mentors = response.mentors;
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

  onAddToFavourites(graduatePaper: GraduatePaperInfo) {
    if (!this.authService.isUser()) {
      this.toastr.warning("Morate se prijaviti");
      return;
    }

    let graduatePaperInfo: GraduatePaperInfo = new GraduatePaperInfo();
    for (let activeFavourites of this.activeAddToFavourites) {
      if (activeFavourites == graduatePaper.id) {
        this.activeAddToFavourites.splice(
          this.activeAddToFavourites.indexOf(graduatePaper.id),
          1
        );
      }
    }

    graduatePaperInfo = graduatePaper;
    this.graduatePaperFavourites.push(graduatePaperInfo);
    window.localStorage.setItem(
      "favourites",
      JSON.stringify(this.graduatePaperFavourites)
    );
    this.toastr.success("Diplomski rad dodat u omiljene");
  }

  getBooksInCart() {
    this.graduatePaperFavourites = JSON.parse(
      window.localStorage.getItem("favourites")
    );
    if (this.graduatePaperFavourites == null) {
      this.graduatePaperFavourites = [];
    } else {
      for (let favourites of this.graduatePaperFavourites) {
        if (this.activeAddToFavourites.includes(favourites.id)) {
          this.activeAddToFavourites.splice(
            this.activeAddToFavourites.indexOf(favourites.id),
            1
          );
        }
      }
    }
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
