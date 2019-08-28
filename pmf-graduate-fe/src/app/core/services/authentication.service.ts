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
  helper = new JwtHelperService();
  decodedToken = this.helper.decodeToken(localStorage.getItem("token"));

  constructor(private http: HttpClient) {}

  public login(loginData: User): Observable<any> {
    return this.http.post(environment.url + "api/auth/login", loginData);
  }

  public register(userData: User): Observable<any> {
    return this.http.post(environment.url + "api/auth/register", userData);
  }

  public isAuthenticated(): boolean {
    if (localStorage.getItem("token")) {
      return true;
    }
    return false;
  }

  public isUser() {
    if (this.decodedToken != null) {
      if (this.decodedToken.roles.authority[0] == "USER") {
        return true;
      }
    }
    return false;
  }

  public isAdmin() {
    if (this.decodedToken != null) {
      if (this.decodedToken.roles.authority[0] == "ADMIN") {
        return true;
      }
    }
    return false;
  }
}
