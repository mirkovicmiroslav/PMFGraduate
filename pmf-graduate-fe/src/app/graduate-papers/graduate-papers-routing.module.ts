import { FavouritesComponent } from "./favourites/favourites.component";
import { HomepageComponent } from "./homepage/homepage.component";
import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";

const routes: Routes = [
  {
    path: "",
    component: HomepageComponent
  },
  {
    path: "graduate-paper/favourites",
    component: FavouritesComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GraduatePaperstRoutingModule {}
