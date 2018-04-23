import { Component, OnInit } from '@angular/core';
import { AuthService } from '@basis/angular-components';

import { Usuario } from '../usuario/usuario';

@Component({
  selector: 'app-login-success',
  template: ''
})
export class LoginSuccessComponent implements OnInit {

  constructor(private auth: AuthService<Usuario>) {}

  ngOnInit() {
    this.auth.loginSuccess();
  }
}
