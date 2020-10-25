import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../domain/user";
import {Observable} from "rxjs";
import {CookieService} from "ngx-cookie-service";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private httpClient: HttpClient, private cookieService: CookieService) {
  }

  login(user: User): Observable<any> {
    return this.httpClient.post('api/account/login', user)
      .pipe(map(it => {
        this.cookieService.set("token", it['token']);
        return it;
      }));
  }

  me(): Observable<User> {
    return this.httpClient.get<User>('api/account/me');
  }
}
