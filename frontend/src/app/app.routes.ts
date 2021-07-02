import { RouterModule, Routes } from '@angular/router';

import { DiarioErrosComponent } from './diario-erros/diario-erros.component';
import { LoginSuccessComponent } from '@nuvem/angular-base';
import { ModuleWithProviders } from '@angular/core';
import { ExamesModule } from './exames/exames.module';
import { ExamesComponent } from './exames/Components/exames/exames.component';

export const routes: Routes = [
    {
        path: 'prescricao-medica',
        loadChildren: 'src/app/prescricao-medica/prescricao-medica.module#PrescricaoMedicaModule',
    },
    {
        path: 'diario-erros',
        component: DiarioErrosComponent,
        data: { breadcrumb: 'Diário de Erros' },
    },
    { path: 'login-success', component: LoginSuccessComponent },
    {
        path: 'pacientes',
        loadChildren: 'src/app/pacientes/pacientes.module#PacientesModule',
    },
    {
        path: 'pre-cadastro',
        loadChildren: 'src/app/pacientes/pacientes.module#PacientesModule',
    },
    {
        path: 'triagem',
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
        component: ExamesComponent,
    },
];

export const AppRoutes: ModuleWithProviders<RouterModule> = RouterModule.forRoot(routes);
