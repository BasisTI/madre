import { ConsultaCalendarioComponent } from './components/consulta-calendario/consulta-calendario.component';
import { ListarConsultasComponent } from './components/listar-consultas/listar-consultas.component';
import { Routes } from '@angular/router';
import { EmergenciaComponent } from './components/emergencia/emergencia.component';
import { DetalhaConsultaComponent } from './components/consulta-calendario/detalha-consulta.component';

export const routes: Routes = [
    {
        path: 'emergencia',
        component: EmergenciaComponent,
    },
    {
        path: 'listar-consultas',
        component: ListarConsultasComponent,
    },
    {
        path: 'consulta-calendario',
        component: ConsultaCalendarioComponent,
    },
    {
        path: 'detalha-consulta/:id',
        component: DetalhaConsultaComponent,
    },
];
