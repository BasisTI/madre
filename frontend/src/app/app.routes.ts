import { RouterModule, Routes } from '@angular/router';

import { DiarioErrosComponent } from './diario-erros/diario-erros.component';
import { AuthGuard, LoginSuccessComponent } from '@nuvem/angular-base';
import { ModuleWithProviders, NgModule } from '@angular/core';
import { LoginComponent } from './login/login.component';
import { AuthGuardService } from './util/auth.guard.service';

export const routes: Routes = [
    { 
        path: 'login', 
        component: LoginComponent, 
        data: { breadcrumb: 'Login'}
    },
    {
        path: 'prescricao-medica',
        loadChildren: 'src/app/prescricao-medica/prescricao-medica.module#PrescricaoMedicaModule',
    },
    {
        path: 'diario-erros',
        component: DiarioErrosComponent,
        data: { breadcrumb: 'Diário de Erros' },
    },
    { 
        path: 'login-success', 
        component: LoginSuccessComponent 
    },
    {
        path: 'pacientes',
        loadChildren: 'src/app/pacientes/pacientes.module#PacientesModule',
        canActivate: [AuthGuardService]
    },
    {
        path: 'triagem',
        loadChildren: 'src/app/pacientes/pacientes.module#PacientesModule',
        canActivate: [AuthGuardService]
    },
    {
        path: 'pre-cadastro',
        loadChildren: 'src/app/pacientes/pacientes.module#PacientesModule',
    },
    {
        path: 'consulta',
        loadChildren: 'src/app/consulta/consulta.module#ConsultaModule',
    },
    {
        path: 'internacao',
        loadChildren: 'src/app/internacao/internacao.module#InternacaoModule',
    },
    {
        path: 'farmacia',
        loadChildren: 'src/app/farmacia/farmacia/farmacia.module#FarmaciaModule',
    },
    {
        path: 'suprimentos',
        loadChildren: 'src/app/suprimentos/suprimentos.module#SuprimentosModule',
        data: { breadcrumb: 'Suprimentos' },
    },
    {
        path: 'exames',
        loadChildren: 'src/app/exames/exames.module#ExamesModule',
    },
    {
        path: 'seguranca',
        loadChildren: 'src/app/seguranca/seguranca.module#SegurancaModule',
    },
];

@NgModule({
    imports: [
      RouterModule.forRoot(routes)
    ],
    exports: [RouterModule]
  })
  export class AppRoutingModule { }
