import { GraduatePaperComponent } from "./graduate-paper/graduate-paper.component";
import { HomepageComponent } from "./homepage/homepage.component";
import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";

const routes: Routes = [
  {
    path: "",
    component: HomepageComponent
  },
  {
    path: "graduate-paper/:id",
    component: GraduatePaperComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GraduatePaperstRoutingModule {}
