import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment.prod";

@Injectable({
  providedIn: "root"
})
export class GraduatePaperService {
  constructor(private http: HttpClient, private httpParams: HttpParams) {}

  public getAllGraduatePapers(): Observable<any> {
    return this.http.get(environment.url + "api/graduatePapers");
  }

  public getByID(id: string): Observable<any> {
    return this.http.get(environment.url + "api/graduatePapers/" + id);
  }

  public getPdfFileById(id: string): Observable<any> {
    return this.http.get(environment.url + "api/graduatePapers/getPdf/" + id);
  }

  public getTopMentors(): Observable<any> {
    return this.http.get(environment.url + "api/graduatePapers/getTopMentors");
  }

  public getSearchedFilter(
    title: string,
    author: string,
    mentor: string
  ): Observable<any> {
    this.httpParams = new HttpParams({
      fromObject: { title: title, author: author, mentor: mentor }
    });
    return this.http.get(
      environment.url + "api/graduatePapers/getSearchedFilter",
      {
        params: this.httpParams
      }
    );
  }
}
