import { DispensacaoComponent } from './dispensacao/dispensacao.component';

import { Routes } from '@angular/router';
import { CadastroMedicamentoComponent } from './cadastro-medicamento/cadastro-medicamento.component';
import { MedicamentosComponent } from './medicamentos/medicamentos.component';
import { MedicamentoDetailComponent } from './medicamento-detail/medicamento-detail.component';
import { DispensacaoDetailComponent } from './dispensacao-detail/dispensacao-detail.component';

export const Farmacia: Routes = [
    {
        path: 'dispensacaos',
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
    },
    {
        path: 'dispensacaos/:id/edit',
        component: DispensacaoComponent,
    },
    {
        path: 'dispensacaos/:id/view',
        component: DispensacaoDetailComponent,
    },
];
