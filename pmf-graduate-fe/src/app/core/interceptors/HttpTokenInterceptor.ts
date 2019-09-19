import { Router } from "@angular/router";
import { TokenService } from "../services/token.service";
import { Injectable } from "@angular/core";
import {
  HttpEvent,
  HttpInterceptor,
  HttpHandler,
  HttpRequest
} from "@angular/common/http";
import { Observable, of } from "rxjs";
import { catchError } from "rxjs/operators";

@Injectable()
export class HttpTokenInterceptor implements HttpInterceptor {
  constructor(public tokenService: TokenService, private router: Router) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    request = request.clone({
      setHeaders: {
        Authorization: `Bearer ${this.tokenService.getToken()}`
      }
    });

    return next.handle(request).pipe(
      catchError(err => {
        if (err.status === 401) {
          this.tokenService.destroyToken();
          this.router.navigate(["/"]);
          return of(err);
        }
        throw err;
      })
    );
  }
}
