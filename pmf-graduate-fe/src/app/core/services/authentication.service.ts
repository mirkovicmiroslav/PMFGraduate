import { User } from "./../models/user.model";
import { Login } from "./../models/login.model";
import { environment } from "./../../../environments/environment";
import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

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
}
