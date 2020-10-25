import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {HomeComponent} from "./home/home.component";
import {CanGo} from "./service/can.go";
import {BaseComponent} from "./base/base.component";


const routes: Routes = [
  {
    path: '',component:BaseComponent, children: [
      {path: 'login', component: LoginComponent, canActivate: [CanGo]},
      {path: 'home', component: HomeComponent, canActivate: [CanGo]}
    ]
  }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
