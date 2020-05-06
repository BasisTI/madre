import { FormularioTriagemComponent } from './components/triagem/formulario-triagem/formulario-triagem.component';
import { Component } from '@angular/core';
import { Routes } from '@angular/router';

import { ListaDePacientesComponent } from './components/lista-de-pacientes/lista-de-pacientes.component';
import { FormularioCadastroComponent } from './components/formulario-paciente/formulario-cadastro.component';
import { TriagemComponent } from './components/triagem/triagem.component';

export const routes: Routes = [
    { path: '', component: ListaDePacientesComponent },
    { path: 'cadastro', component: FormularioCadastroComponent },
    { path: 'triagem', component: TriagemComponent },
    { path: 'formulario', component: FormularioTriagemComponent },
];
