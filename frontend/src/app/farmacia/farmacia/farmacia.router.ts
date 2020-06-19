import { DispensacaoMedicamentoComponent } from './dispensacao/dispensacao-medicamento/dispensacao-medicamento.component';
import { Component } from '@angular/core';
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
    {
        path: 'dispensacao-medicamentos/:id',
        component: DispensacaoMedicamentoComponent,
    },
];
