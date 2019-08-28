import { AuthenticationService } from "./../../core/services/authentication.service";
import { User } from "./../../core/models/user.model";
import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { ToastrService } from "ngx-toastr";

@Component({
  selector: "app-register",
  templateUrl: "./register.component.html",
  styleUrls: ["./register.component.css"]
})
export class RegisterComponent implements OnInit {
  userData: User = new User();
  rPassword = "";

  constructor(
    private authService: AuthenticationService,
    private router: Router,
    private toastr: ToastrService
  ) {}

  ngOnInit() {}

  onRegister() {
    if (this.rPassword !== this.userData.password) {
      this.toastr.error("Passwords do not match!", "Unable to register");
    } else {
      this.authService.register(this.userData).subscribe(
        response => {
          this.toastr.success("Successfully registration");
          this.router.navigate(["/login"]);
        },
        error => {
          this.toastr.error(error.error, "Unable to register");
        }
      );
    }
  }
}
