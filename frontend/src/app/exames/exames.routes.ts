import { Routes } from '@angular/router';
import {ExamesComponent} from "./Components/exames/exames.component";

export const routes: Routes = [
    {
        path: 'solicitar-exame',
        component: ExamesComponent,
        data: { breadcrumb: 'Solicitar Exames' },
    },
    {
        path: 'atendimento-diverso',
    }
];
