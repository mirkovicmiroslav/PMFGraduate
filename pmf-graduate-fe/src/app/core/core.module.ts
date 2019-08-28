import { IsUserGuard } from "./guards/isUser.guard";
import { IsAdminGuard } from "./guards/isAdmin.guard";
import { AuthenticationGuard } from "./guards/authentication.guard";
import { HttpTokenInterceptor } from "./interceptors/HttpTokenInterceptor";
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import {
  HttpClientModule,
  HttpParams,
  HTTP_INTERCEPTORS
} from "@angular/common/http";

@NgModule({
  declarations: [],
  imports: [CommonModule],
  exports: [HttpClientModule],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: HttpTokenInterceptor, multi: true },
    HttpParams,
    AuthenticationGuard,
    IsAdminGuard,
    IsUserGuard
  ]
})
export class CoreModule {}
