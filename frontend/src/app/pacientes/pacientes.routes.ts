import { ListagemPreCadastroComponent } from './components/listagem-pre-cadastro/listagem-pre-cadastro.component';
import { PreCadastroComponent } from './components/pre-cadastro/pre-cadastro.component';
import { SolicitacaoDeInternacaoComponent } from './../internacao/components/solicitacao-de-internacao/solicitacao-de-internacao.component';
import { FormularioTriagemComponent } from './components/triagem/formulario-triagem/formulario-triagem.component';
import { Routes } from '@angular/router';

import { TriagemComponent } from './components/triagem/triagem.component';

export const routes: Routes = [
    { path: 'lista-pre-cadastro', component: ListagemPreCadastroComponent },
    { path: 'pre-cadastro', component: PreCadastroComponent },
    { path: 'triagem', component: TriagemComponent },
    { path: 'formulario', component: FormularioTriagemComponent },
    { path: 'formulario/edit/:id', component: FormularioTriagemComponent },
    { path: 'formulario/view/:id', component: FormularioTriagemComponent },
    { path: 'edit/:id', component: FormularioTriagemComponent },
    { path: 'solicitacao-de-internacao', component: SolicitacaoDeInternacaoComponent },
];
