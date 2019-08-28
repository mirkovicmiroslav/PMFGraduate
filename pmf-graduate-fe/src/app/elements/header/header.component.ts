import { AuthenticationService } from "./../../core/services/authentication.service";
import { TokenService } from "src/app/core/services/token.service";
import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-header",
  templateUrl: "./header.component.html",
  styleUrls: ["./header.component.css"]
})
export class HeaderComponent implements OnInit {
  constructor(
    public authService: AuthenticationService,
    private tokenService: TokenService
  ) {}

  ngOnInit() {}

  onLogout() {
    this.tokenService.destroyToken();
    location.reload();
  }
}
