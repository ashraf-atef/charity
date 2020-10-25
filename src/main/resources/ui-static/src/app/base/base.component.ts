import {Component, OnInit} from '@angular/core';
import {AccountService} from "../service/account.service";
import {CookieService} from "ngx-cookie-service";
import {AuthService} from "../service/auth.service";

@Component({
  selector: 'us-base',
  templateUrl: './base.component.html',
  styleUrls: ['./base.component.css']
})
export class BaseComponent implements OnInit {

  constructor(private accountService: AccountService, private cookieService: CookieService, private authService: AuthService) {
  }

  ngOnInit() {
    this.accountService.me().subscribe(it => {
      this.authService.user = it;
    }, error => {
      this.authService.user = null;
      this.cookieService.delete("token");
    })
  }

}
