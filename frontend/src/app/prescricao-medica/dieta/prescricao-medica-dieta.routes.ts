import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PrescricaoMedicaDietaComponent } from './../dieta/prescricao-medica-dieta.component';

export const routes: Routes = [
    {
        path: 'dieta',
        component: PrescricaoMedicaDietaComponent,
    },
    {
        path: 'prescricao-medica/dieta',
        component: PrescricaoMedicaDietaComponent,
    },

    {
        path: 'prescricao-medica/dieta/:id',
        component: PrescricaoMedicaDietaComponent,
    },
];

export const PrescricaoMedicaDietaRoutes: ModuleWithProviders<RouterModule> =
    RouterModule.forRoot(routes);
