import { AddGraduatePaperComponent } from "./add-graduate-paper/add-graduate-paper.component";
import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";

const routes: Routes = [
  {
    path: "add-graduate-paper",
    component: AddGraduatePaperComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminManagementRoutingModule {}
