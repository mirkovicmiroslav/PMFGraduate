import { GraduatePaper } from "src/app/core/models/graduatePaper.model";
import { GraduatePaperService } from "./../../core/services/graduatePaper.service";
import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";

@Component({
  selector: "app-graduate-paper",
  templateUrl: "./graduate-paper.component.html",
  styleUrls: ["./graduate-paper.component.css"]
})
export class GraduatePaperComponent implements OnInit {
  graduatePaper: GraduatePaper = new GraduatePaper();

  constructor(
    private graduatePaperService: GraduatePaperService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      if (params["id"]) {
        this.graduatePaperService.getByID(params["id"]).subscribe(response => {
          this.graduatePaper = response;
          console.log(this.graduatePaper);
        });
      }
    });
  }
}
