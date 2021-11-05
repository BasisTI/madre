import { Routes } from '@angular/router';
import { LoginComponent } from './login.component';


export const loginRoute: Routes = [
    {
        path: 'login',
        component: LoginComponent,
        data: {
            breadcrumb: "Login"
        }
    }
];
