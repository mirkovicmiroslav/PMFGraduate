import { GraduatePaperService } from "../../core/services/graduatePaper.service";
import { GraduatePaperList } from "../../core/models/graduatePaperList.model";
import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-homepage",
  templateUrl: "./homepage.component.html",
  styleUrls: ["./homepage.component.css"]
})
export class HomepageComponent implements OnInit {
  graduatePaperList: GraduatePaperList = new GraduatePaperList();

  constructor(private graduatePaperService: GraduatePaperService) {}

  ngOnInit() {
    this.graduatePaperService.getAllGraduatePapers().subscribe(response => {
      this.graduatePaperList.graduatePapers = response.graduatePapers;
    });
  }
}
