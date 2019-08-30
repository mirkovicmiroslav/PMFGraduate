import { environment } from "./../../../environments/environment";
import { GraduatePaper } from "./../models/graduatePaper.model";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root"
})
export class AdminManagementService {
  constructor(private http: HttpClient) {}

  saveGraduatePaper(graduatePaper: GraduatePaper): Observable<any> {
    return this.http.post(
      environment.url + "api/admin/saveGraduatePaper",
      graduatePaper
    );
  }

  uploadFile(fileToUpload: File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append("file", fileToUpload, fileToUpload.name);

    return this.http.post(environment.url + "api/admin/uploadFile", formData);
  }
}
