import { DispensacaoComponent } from './dispensacao/dispensacao.component';
import { FarmaciaComponent } from './farmacia.component';
import { Routes } from '@angular/router';

export const Farmacia: Routes = [
    {
        path: 'farmacia',
        component: DispensacaoComponent,
    },
];
