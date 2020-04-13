import { Component } from '@angular/core';
import { AppComponent } from './app.component';
import { AbstractAuthorization, User } from '@nuvem/angular-base';

@Component({
    selector: 'app-topbar',
    templateUrl: './app.topbar.component.html',
})
export class AppTopbarComponent {
    constructor(public app: AppComponent, private authorization: AbstractAuthorization<User>) {}

    get usuario() {
        return this.authorization.getUser();
    }
}
