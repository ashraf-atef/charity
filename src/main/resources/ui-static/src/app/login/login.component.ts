import {Component, OnInit} from '@angular/core';
import {User} from "../domain/user";
import {AccountService} from "../service/account.service";
import {Form} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'us-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: User = new User();
  errorMessage: string = undefined;

  constructor(private accountService: AccountService, private router: Router) {
  }

  ngOnInit() {
  }

  login(form: Form) {
    this.accountService.login(this.user).subscribe(it => {
      this.errorMessage = undefined;
      this.router.navigate(['/home'])
    }, error => {
      this.errorMessage = 'Error Username or Password';
    });
  }
}
