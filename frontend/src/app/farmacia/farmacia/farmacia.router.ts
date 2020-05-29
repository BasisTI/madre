import { DispensacaoComponent } from './dispensacao/dispensacao.component';

import { Routes } from '@angular/router';
import { MedicamentosComponent } from './medicamentos/medicamentos.component';

export const Farmacia: Routes = [
    {
        path: 'farmacia',
        component: DispensacaoComponent,
    },
    {
        path: 'medicamentos',
        component: MedicamentosComponent,
    },
];
