import { FormularioTriagemComponent } from './triagem/formulario-triagem/formulario-triagem.component';
import { Component } from '@angular/core';
import { Routes } from '@angular/router';

import { ListaDePacientesComponent } from './lista-de-pacientes/lista-de-pacientes.component';
import { FormularioCadastroComponent } from './formulario-paciente/formulario-cadastro.component';
import { TriagemComponent } from './triagem/triagem.component';
import { SolicitacaoDeInternacaoComponent } from './solicitacao-de-internacao/solicitacao-de-internacao.component';

export const routes: Routes = [
    { path: '', component: ListaDePacientesComponent },
    { path: 'cadastro', component: FormularioCadastroComponent },

    { path: 'triagem', component: TriagemComponent },
    { path: 'formulario', component: FormularioTriagemComponent },
    { path: 'solicitacao-de-internacao', component: SolicitacaoDeInternacaoComponent },
];
