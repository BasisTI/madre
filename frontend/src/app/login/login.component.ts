import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '@nuvem/angular-base';
import { PageNotificationService } from '@nuvem/primeng-components';
import { Usuario } from '../usuario/usuario';
import { LoginService } from './login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  login: string;
  codigo: string;
  authenticated = false;

  constructor(
    private pageNotificationService: PageNotificationService,
    private router: Router,
    private loginService: LoginService,
    private authService: AuthenticationService<Usuario>,
  ) { }
  getLabel(label) {
    return label;
  }

  ngOnInit(): void {
  }

  logar() {

    if (!this.login || !this.codigo) {
      this.pageNotificationService.addErrorMessage(this.getLabel('Por favor preencher campos obrigatórios'));
      return;
    }

    if (this.codigo.length < 4) {
      this.pageNotificationService.addErrorMessage(this.getLabel('A senha precisa ter no mínimo 4 caracteres!'));
      return;
    }

    this.loginService.login(this.login, this.codigo);
    this.authService.login();
    this.router.navigate(['/']);
  }

}
