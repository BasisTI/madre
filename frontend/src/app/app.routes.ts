import { PrescricaoMedicaComponent } from './prescricao-medica/prescricao-medica.component';
import { Routes, RouterModule } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { DiarioErrosComponent } from './diario-erros/diario-erros.component';
import { LoginSuccessComponent } from '@nuvem/angular-base';

export const routes: Routes = [
    {
        path: 'prescricao-medica',
        loadChildren: 'src/app/prescricao-medica/prescricao-medica.module#PrescricaoMedicaModule',
    },
    { path: 'diario-erros', component: DiarioErrosComponent },
    { path: 'login-success', component: LoginSuccessComponent },
    {
        path: 'pacientes',
        loadChildren: 'src/app/pacientes/pacientes.module#PacientesModule',
    },
    {
        path: 'triagem',
        loadChildren: 'src/app/pacientes/pacientes.module#PacientesModule',
    },
    {
        path: 'solicitacao-de-internacao',
        loadChildren: 'src/app/pacientes/pacientes.module#PacientesModule',
    },
    {
        path: 'farmacia',
        loadChildren: 'src/app/farmacia/farmacia/farmacia.module#FarmaciaModule',
    },
];

export const AppRoutes: ModuleWithProviders = RouterModule.forRoot(routes);
