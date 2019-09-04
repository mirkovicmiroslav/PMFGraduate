import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment.prod";

@Injectable({
  providedIn: "root"
})
export class GraduatePaperService {
  constructor(private http: HttpClient) {}

  public getAllGraduatePapers(): Observable<any> {
    return this.http.get(environment.url + "api/graduatePapers");
  }

  public getByID(id: string): Observable<any> {
    return this.http.get(environment.url + "api/graduatePapers/" + id);
  }

  public getPdfFileById(id: string): Observable<any> {
    return this.http.get(environment.url + "api/graduatePapers/getPdf/" + id);
  }
}
