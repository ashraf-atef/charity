import {Injectable} from '@angular/core';
import {User} from "../domain/user";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private _user: User = null;

  constructor() {
  }

  get user(): User {
    return this._user;
  }

  set user(value: User) {
    this._user = value;
  }

  public isAuth() {
    return this._user != null;
  }
}
