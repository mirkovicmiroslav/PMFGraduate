import { ToastrService } from "ngx-toastr";
import { AdminManagementService } from "./../../core/services/admin-management.service";
import { Component, OnInit } from "@angular/core";
import { GraduatePaper } from "src/app/core/models/graduatePaper.model";
import { Router } from "@angular/router";

@Component({
  selector: "app-add-graduate-paper",
  templateUrl: "./add-graduate-paper.component.html",
  styleUrls: ["./add-graduate-paper.component.css"]
})
export class AddGraduatePaperComponent implements OnInit {
  graduatePaper: GraduatePaper = new GraduatePaper();
  pdf;
  file;

  constructor(
    private adminService: AdminManagementService,
    private toastr: ToastrService,
    private router: Router
  ) {}

  ngOnInit() {}

  onFileSelected(event) {
    this.file = event.target.files[0];
  }

  onCreateGraduatePaper() {
    this.adminService.uploadFile(this.file).subscribe(
      response => {
        this.graduatePaper.pdfFile = response.fileId;
        this.adminService.saveGraduatePaper(this.graduatePaper).subscribe(
          response => {
            this.router.navigate(["/"]);
          },
          error => {
            this.toastr.error("Failed!");
          }
        );
      },
      error => {
        this.toastr.error("Failed to upload file");
      }
    );
  }
}
