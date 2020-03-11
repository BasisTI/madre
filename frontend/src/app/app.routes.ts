import { Routes, RouterModule } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { DiarioErrosComponent } from './diario-erros/diario-erros.component';
import { LoginSuccessComponent } from '@nuvem/angular-base';

export const routes: Routes = [
    { path: 'diario-erros', component: DiarioErrosComponent },
    { path: 'login-success', component: LoginSuccessComponent },
];

export const AppRoutes: ModuleWithProviders = RouterModule.forRoot(routes);
