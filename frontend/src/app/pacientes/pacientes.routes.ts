import { ListagemPreCadastroComponent } from './components/listagem-pre-cadastro/listagem-pre-cadastro.component';
import { PreCadastroComponent } from './components/pre-cadastro/pre-cadastro.component';
import { SolicitacaoDeInternacaoComponent } from './../internacao/components/solicitacao-de-internacao/solicitacao-de-internacao.component';
import { FormularioTriagemComponent } from './components/triagem/formulario-triagem/formulario-triagem.component';
import { Component } from '@angular/core';
import { Routes } from '@angular/router';

import { ListaDePacientesComponent } from './components/lista-de-pacientes/lista-de-pacientes.component';
import { FormularioCadastroComponent } from './components/formulario-paciente/formulario-cadastro.component';
import { TriagemComponent } from './components/triagem/triagem.component';

export const routes: Routes = [
    { path: '', component: ListaDePacientesComponent },
    { path: 'cadastro', component: FormularioCadastroComponent },
    { path: 'lista-pre-cadastro', component: ListagemPreCadastroComponent },
    { path: 'pre-cadastro', component: PreCadastroComponent },
    { path: 'triagem', component: TriagemComponent },
    { path: 'formulario', component: FormularioTriagemComponent },
    { path: 'edit/:id', component: FormularioTriagemComponent },
    { path: 'solicitacao-de-internacao', component: SolicitacaoDeInternacaoComponent },
];
