import { ModuleWithProviders } from '@angular/core';
import { MedicamentoComponent } from './medicamento.component';
import { Routes, RouterModule } from '@angular/router';

export const medicamentoRoute: Routes = [

    {
        path: 'prescricao-medica/medicamento',
        component: MedicamentoComponent
    },

    {
        path: 'prescricao-medica/medicamento/:id',
        component: MedicamentoComponent
    },
];

export const MedicamentoRoutes: ModuleWithProviders<RouterModule> = RouterModule.forRoot(medicamentoRoute);

