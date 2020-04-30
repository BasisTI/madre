import { ModuleWithProviders } from '@angular/core';
import { MedicamentoComponent } from './medicamento.component';
import { Routes, RouterModule } from '@angular/router';

export const medicamentoRoute: Routes = [

    {
        path: 'prescricao-medica/medicamento',
        component: MedicamentoComponent
    }
];

export const MedicamentoRoutes: ModuleWithProviders = RouterModule.forRoot(medicamentoRoute);

