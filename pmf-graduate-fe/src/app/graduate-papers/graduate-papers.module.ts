import { PdfViewerModule } from "ng2-pdf-viewer";
import { HomepageComponent } from "./homepage/homepage.component";
import { GraduatePaperstRoutingModule } from "./graduate-papers-routing.module";
import { FormsModule } from "@angular/forms";
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";

@NgModule({
  declarations: [HomepageComponent],
  imports: [
    CommonModule,
    FormsModule,
    PdfViewerModule,
    GraduatePaperstRoutingModule
  ]
})
export class GraduatePapersModule {}
