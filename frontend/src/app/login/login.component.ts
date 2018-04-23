import { Component, OnInit } from '@angular/core';
import { AuthService } from '@basis/angular-components';

import { Usuario } from '../usuario/usuario';

@Component({
  selector: 'app-login',
  template: ''
})
export class LoginComponent implements OnInit {

  constructor(private auth: AuthService<Usuario>) {}

  ngOnInit() {
    this.auth.redirectLogin();
  }
}
