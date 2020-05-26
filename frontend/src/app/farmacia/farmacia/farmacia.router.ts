import { DispensacaoComponent } from './dispensacao/dispensacao.component';
import { FarmaciaComponent } from './farmacia.component';
import { Routes } from '@angular/router';
import { CadastroMedicamentoComponent } from './cadastro-medicamento/cadastro-medicamento.component';

export const Farmacia: Routes = [
    {
        path: 'farmacia',
        component: DispensacaoComponent,
    },
    {
        path: 'cadastrar-medicamento',
        component: CadastroMedicamentoComponent,
    },
];
