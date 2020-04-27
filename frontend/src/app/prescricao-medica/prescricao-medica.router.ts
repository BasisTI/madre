import { PrescricaoMedicaComponent } from './prescricao-medica.component';
import { PrescricaoMedicaDietaComponent } from './dieta/prescricao-medica-dieta.component';
import { Routes } from '@angular/router';

export const prescricaoMedica: Routes = [
    {
        path: 'prescricao-medica',
        component: PrescricaoMedicaComponent
    },

    {
        path: 'prescricao-medica/dieta/:id',
        component: PrescricaoMedicaDietaComponent
    },
]


