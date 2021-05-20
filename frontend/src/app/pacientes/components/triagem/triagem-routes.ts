import { ClassificacaoDeRiscoComponent } from './formulario-triagem/classificacao-de-risco/classificacao-de-risco.component';
import { FormularioTriagemComponent } from './formulario-triagem/formulario-triagem.component';
import { TriagemComponent } from './triagem.component';
import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
    {
        path: 'triagem',
        component: TriagemComponent,
    },
    {
        path: 'formulario',
        component: FormularioTriagemComponent,
    },

    {
        path: 'formulario/:id',
        component: FormularioTriagemComponent,
    },

    {
        path: 'classificacao',
        component: ClassificacaoDeRiscoComponent,
    },
];

export const TriagemComponentRoutes: ModuleWithProviders<RouterModule> = RouterModule.forRoot(routes);
