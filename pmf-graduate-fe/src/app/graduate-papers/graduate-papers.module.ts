import { GraduatePaperComponent } from "./graduate-paper/graduate-paper.component";
import { HomepageComponent } from "./homepage/homepage.component";
import { GraduatePaperstRoutingModule } from "./graduate-papers-routing.module";
import { FormsModule } from "@angular/forms";
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";

@NgModule({
  declarations: [HomepageComponent, GraduatePaperComponent],
  imports: [CommonModule, FormsModule, GraduatePaperstRoutingModule]
})
export class GraduatePapersModule {}
