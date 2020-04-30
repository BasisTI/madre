import { InternacaoDePacienteComponent } from './components/internacao-de-paciente/internacao-de-paciente.component';
import { Routes } from '@angular/router';
import { SolicitacaoDeInternacaoComponent } from './components/solicitacao-de-internacao/solicitacao-de-internacao.component';

export const routes: Routes = [
    {
        path: 'solicitacao-de-internacao',
        component: SolicitacaoDeInternacaoComponent,
    },
    {
        path: 'internacao-de-paciente',
        component: InternacaoDePacienteComponent,
    },
];
