import { Component } from '@angular/core';
import { AppComponent } from './app.component';
import { Authentication, User } from '@nuvem/angular-base';

@Component({
    selector: 'app-topbar',
    templateUrl: './app.topbar.component.html',
})
export class AppTopbarComponent {
    constructor(public app: AppComponent, private _authentication: Authentication<User>) {}

    get usuario() {
        return this._authentication.getUser();
    }
}
