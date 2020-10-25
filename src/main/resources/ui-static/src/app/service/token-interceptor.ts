import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {Injectable} from "@angular/core";
import {CookieService} from "ngx-cookie-service";

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(private cookieService: CookieService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token: string = this.cookieService.get('token');
    if (token) req = req.clone({headers: req.headers.set('Authorization', 'Bearer ' + token)});
    return next.handle(req);
  }

}
