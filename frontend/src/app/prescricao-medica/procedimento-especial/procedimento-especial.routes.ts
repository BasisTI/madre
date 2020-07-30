import { Routes, RouterModule } from '@angular/router';
import { ProcedimentoEspecialComponent } from './procedimento-especial.component';
import { ModuleWithProviders } from '@angular/core';

export const procedimentoEspecialRoute: Routes = [
    {
        path: 'prescricao-medica/procedimento-especial',
        component: ProcedimentoEspecialComponent
    },

    {
        path: 'prescricao-medica/procedimento-especial/:id',
        component: ProcedimentoEspecialComponent
    },
];

export const ProcedimentoEspecialRoutes: ModuleWithProviders = RouterModule.forRoot(procedimentoEspecialRoute);
