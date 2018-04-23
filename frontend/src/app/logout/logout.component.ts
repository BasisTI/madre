import { Component, OnInit } from '@angular/core';
import { AuthService } from '@basis/angular-components';

import { Usuario } from '../usuario/usuario';

@Component({
  selector: 'app-logout',
  template: ''
})
export class LogoutComponent implements OnInit {

  constructor(private auth: AuthService<Usuario>) {}

  ngOnInit() {
    this.auth.logout();
  }
}
