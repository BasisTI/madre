import { DispensacaoComponent } from './dispensacao/dispensacao.component';

import { Routes } from '@angular/router';
import { CadastroMedicamentoComponent } from './cadastro-medicamento/cadastro-medicamento.component';
import { MedicamentosComponent } from './medicamentos/medicamentos.component';
import { MedicamentoDetailComponent } from './medicamento-detail/medicamento-detail.component';

export const Farmacia: Routes = [
    {
        path: 'farmacia',
        component: DispensacaoComponent,
    },
    {
        path: 'cadastrar-medicamento',
        component: CadastroMedicamentoComponent,
    },
    {
        path: 'medicamentos',
        component: MedicamentosComponent,
    },
    {
        path: 'medicamentos/:id/edit', 
        component: CadastroMedicamentoComponent,
    },
    {
        path: 'medicamentos/:id/view',
        component: MedicamentoDetailComponent,
    }
];
