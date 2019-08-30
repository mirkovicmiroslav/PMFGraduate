import { IsAdminGuard } from "./core/guards/isAdmin.guard";
import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";

const routes: Routes = [
  {
    path: "",
    loadChildren:
      "./graduate-papers/graduate-papers.module#GraduatePapersModule"
  },
  {
    path: "",
    loadChildren: "./authentication/authentication.module#AuthenticationModule"
  },
  {
    path: "admin",
    loadChildren:
      "./admin-management/admin-management.module#AdminManagementModule",
    canActivate: [IsAdminGuard]
  },
  {
    path: "**",
    redirectTo: "/"
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
