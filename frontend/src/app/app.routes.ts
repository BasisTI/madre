import { Routes, RouterModule } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';

import { LoginComponent } from './login/login.component';
import { LoginSuccessComponent } from './login-success/login-success.component';
import { LogoutComponent } from './logout/logout.component';
import { DiarioErrosComponent } from './diario-erros/diario-erros.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'login-success', component: LoginSuccessComponent },
  { path: 'logout', component: LogoutComponent },
  { path: 'diario-erros', component: DiarioErrosComponent },
  /* jhipster-needle-add-lazy-module - JHipster will add lazy modules here */
];

export const AppRoutes: ModuleWithProviders = RouterModule.forRoot(routes);
