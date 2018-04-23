import { Component, OnInit } from '@angular/core';
import { AuthService } from '@basis/angular-components';

import { AppComponent } from './app.component';
import { Usuario } from './usuario/usuario';

@Component({
    selector: 'app-topbar',
    templateUrl: './app.topbar.component.html'
})
export class AppTopBarComponent implements OnInit {

    usuario: Usuario;

    constructor(public app: AppComponent, private auth: AuthService<Usuario>) {}

    ngOnInit() {
        this.usuario = this.auth.getUser();
    }
}
