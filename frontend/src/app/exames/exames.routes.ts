import { Routes } from '@angular/router';
import {ExamesComponent} from "./Components/exames/exames.component";

export const routesExam: Routes = [
    {
        path: 'solicitar-exame',
        component: ExamesComponent,
    },
];
