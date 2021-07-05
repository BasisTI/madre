import { Routes } from '@angular/router';
// import {ExamesComponent} from "./Components/exames/exames.component";
import { SolicitarExameComponent } from './views/solicitar-exame/solicitar-exame.component';

export const routesExam: Routes = [
    {
        path: 'solicitar-exame',
        component: SolicitarExameComponent,
    },
];
