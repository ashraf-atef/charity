import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Observable, of} from "rxjs";
import {Injectable} from "@angular/core";
import {AuthService} from "./auth.service";
import {AccountService} from "./account.service";
import {catchError, map} from "rxjs/operators";

@Injectable()
export class CanGo implements CanActivate {

  constructor(private router: Router, private authService: AuthService, private accountService: AccountService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.authService.isAuth()) {
      if ('/login' == state.url) {
        return this.router.parseUrl("/home");
      } else return true;
    } else {
      return this.accountService.me().pipe(map(it => {
        this.authService.user = it;
        if ('/login' == state.url) {
          return this.router.parseUrl("/home");
        } else return true;
      }), catchError(it => {
        if ('/login' == state.url) {
          return of(true);
        }
        return of(this.router.parseUrl("/login"));
      }))
    }
  }
}
