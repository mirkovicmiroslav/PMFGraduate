import { TokenService } from "src/app/core/services/token.service";
import { User } from "./../models/user.model";
import { environment } from "./../../../environments/environment";
import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { JwtHelperService } from "@auth0/angular-jwt";

@Injectable({
  providedIn: "root"
})
export class AuthenticationService {
  constructor(private http: HttpClient, private tokenService: TokenService) {}

  public login(loginData: User): Observable<any> {
    return this.http.post(environment.url + "api/auth/login", loginData);
  }

  public register(userData: User): Observable<any> {
    return this.http.post(environment.url + "api/auth/register", userData);
  }

  public isAuthenticated(): boolean {
    if (this.tokenService.getToken()) {
      return true;
    }
    return false;
  }

  public isUser(): boolean {
    const helper = new JwtHelperService();
    const decodedToken = helper.decodeToken(this.tokenService.getToken());
    if (decodedToken != null) {
      if (decodedToken.roles[0].authority == "USER") {
        return true;
      }
    }
    return false;
  }

  public isAdmin(): boolean {
    const helper = new JwtHelperService();
    const decodedToken = helper.decodeToken(this.tokenService.getToken());
    if (decodedToken != null) {
      if (decodedToken.roles[0].authority == "ADMIN") {
        return true;
      }
    }
    return false;
  }
}
