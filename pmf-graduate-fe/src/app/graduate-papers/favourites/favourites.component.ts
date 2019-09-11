import { GraduatePaper } from "src/app/core/models/graduatePaper.model";
import { PdfViewerComponent } from "ng2-pdf-viewer";
import { GraduatePaperService } from "./../../core/services/graduatePaper.service";
import { GraduatePaperInfo } from "./../../core/models/graduatePaperInfo.model";
import { Component, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { Subscription } from "rxjs";
import { ToastrService } from "ngx-toastr";

@Component({
  selector: "app-favourites",
  templateUrl: "./favourites.component.html",
  styleUrls: ["./favourites.component.css"]
})
export class FavouritesComponent implements OnInit, OnDestroy {
  private subscription: Subscription = new Subscription();
  graduatePaperFavourites: GraduatePaperInfo[] = [];
  activeAddToFavourites: Number[] = [];
  pdfSrc: any;
  @ViewChild(PdfViewerComponent, { static: false })
  public pdfComponent: PdfViewerComponent;
  graduatePaper: GraduatePaper = new GraduatePaper();
  page: number = 1;
  totalPages: number;
  pdfQuery = "";
  pdf: any;

  constructor(
    private graduatePaperService: GraduatePaperService,
    private toastr: ToastrService
  ) {}

  ngOnInit() {
    this.subscription.add(this.getBooksInCart());
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
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

  onOpenGraduatePaper(id: string) {
    this.graduatePaperService.getPdfFileById(id).subscribe(response => {
      this.pdfSrc = this.base64ToArrayBuffer(response.pdfBytes);
    });
  }

  base64ToArrayBuffer(base64: any) {
    let binary_string = window.atob(base64);
    let len = binary_string.length;
    let bytes = new Uint8Array(len);
    for (let i = 0; i < len; i++) {
      bytes[i] = binary_string.charCodeAt(i);
    }
    return bytes.buffer;
  }

  onRemoveFromFavourites(graduatePaperId: any) {
    this.graduatePaperFavourites.splice(graduatePaperId, 1);
    window.localStorage.setItem(
      "favourites",
      JSON.stringify(this.graduatePaperFavourites)
    );
    this.toastr.success("Diplomski rad uklonjen iz omiljenih");
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
