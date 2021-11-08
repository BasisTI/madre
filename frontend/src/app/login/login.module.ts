import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login.component';
import { LoginService } from './login.service';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { loginRoute } from './login.route';
import { AuthorizationService, SecurityModule } from '@nuvem/angular-base';



@NgModule({
  imports: [
    CommonModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(loginRoute, {useHash:true}),
    SecurityModule

  ],
  declarations: [
    LoginComponent
  ],
  providers: [
    LoginService,
    AuthorizationService,
    //CookieService
  ],
  exports: [
    LoginComponent
  ]
})
export class LoginModule { }
