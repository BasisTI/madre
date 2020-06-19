import { RecebimentoComponent } from './recebimento/recebimento.component';
import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: 'recebimento',
        component: RecebimentoComponent,
        data: { breadcrumb: 'Gerar Nota de Recebimento' },
    },
];
