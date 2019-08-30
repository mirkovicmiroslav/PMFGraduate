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
    const helper = new JwtHelperService();
    const decodedToken = helper.decodeToken(localStorage.getItem("token"));
    if (decodedToken != null) {
      if (decodedToken.roles[0].authority == "USER") {
        return true;
      }
    }
    return false;
  }

  public isAdmin() {
    const helper = new JwtHelperService();
    const decodedToken = helper.decodeToken(localStorage.getItem("token"));
    if (decodedToken != null) {
      if (decodedToken.roles[0].authority == "ADMIN") {
        return true;
      }
    }
    return false;
  }
}
