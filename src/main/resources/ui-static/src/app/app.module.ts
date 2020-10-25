import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {HomeComponent} from './home/home.component';
import {FormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";
import {TokenInterceptor} from "./service/token-interceptor";
import {CanGo} from "./service/can.go";
import { BaseComponent } from './base/base.component';


export const httpInterceptorProviders = [
  {provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true},
];


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    BaseComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule
  ],
  providers: [CanGo, CookieService, httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule {
}
