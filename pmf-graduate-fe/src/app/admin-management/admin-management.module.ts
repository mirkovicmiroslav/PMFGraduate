import { AddGraduatePaperComponent } from "./add-graduate-paper/add-graduate-paper.component";
import { AdminManagementRoutingModule } from "./admin-management-routing.module";
import { FormsModule } from "@angular/forms";
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";

@NgModule({
  declarations: [AddGraduatePaperComponent],
  imports: [CommonModule, FormsModule, AdminManagementRoutingModule]
})
export class AdminManagementModule {}
