import { DispensacaoComponent } from './dispensacao/dispensacao.component';

import { Routes } from '@angular/router';
import { CadastroMedicamentoComponent } from './cadastro-medicamento/cadastro-medicamento.component';
import { MedicamentosComponent } from './medicamentos/medicamentos.component';

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
];
