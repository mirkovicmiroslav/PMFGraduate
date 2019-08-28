import { Router } from "@angular/router";
import { AuthenticationService } from "./../../core/services/authentication.service";
import { User } from "./../../core/models/user.model";
import { Component, OnInit } from "@angular/core";
import { TokenService } from "src/app/core/services/token.service";
import { ToastrService } from "ngx-toastr";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["../register/register.component.css"]
})
export class LoginComponent implements OnInit {
  loginData: User = new User();

  constructor(
    private authService: AuthenticationService,
    private tokenService: TokenService,
    private toastr: ToastrService,
    private router: Router
  ) {}

  ngOnInit() {}

  onLogin() {
    this.authService.login(this.loginData).subscribe(
      response => {
        this.tokenService.saveToken(response.accessToken);
        this.router.navigate(["/"]);
      },
      error => {
        this.toastr.error(error.error, "Unable to login");
      }
    );
  }
}
