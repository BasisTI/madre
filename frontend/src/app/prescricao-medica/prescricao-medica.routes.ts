import { ListaPrescricaoComponent } from './lista-prescricao/lista-prescricao.component';
import { MedicamentoComponent } from './medicamento/medicamento.component';
import { PrescricaoMedicaComponent } from './prescricao-medica.component';
import { PrescricaoMedicaDietaComponent } from './dieta/prescricao-medica-dieta.component';
import { Routes } from '@angular/router';
import { DiagnosticoComponent } from './diagnostico/diagnostico.component';

export const prescricaoMedica: Routes = [
    {
        path: 'prescricao-medica',
        component: PrescricaoMedicaComponent
    },

    {
        path: 'prescricao-medica/dieta/:id',
        component: PrescricaoMedicaDietaComponent
    },

    {
        path: 'prescricao-medica/medicamento/:id',
        component: MedicamentoComponent
    },
    {
        path: 'prescricao-medica/diagnostico/:id',
        component: DiagnosticoComponent
    },

    {
        path: 'prescricao-medica/lista/:id',
        component: ListaPrescricaoComponent
    },
]


