import { AgendamentoComponent } from './agendamento/agendamento.component';
import { AtendimentoComponent } from './atendimento/atendimento.component';
import { CadastrosComponent } from './cadastros/cadastros.component';
import { ConsultasComponent } from './consultas/consultas.component';
import { PacientesComponent } from './pacientes/pacientes.component';
import { Routes } from '@angular/router';

export const AmbulatorioRoute: Routes = [
  {
    path: 'agendamento',
    component: AgendamentoComponent
  },
  {
    path: 'agendamento/new',
    component: AgendamentoComponent
  },
  {
    path: 'agendamento/:id/edit',
    component: AgendamentoComponent
  },
  {
    path: 'agendamento/:id',
    component: AgendamentoComponent
  },

  {
    path: 'atendimento',
    component: AtendimentoComponent
  },
  {
    path: 'atendimento/new',
    component: AtendimentoComponent
  },
  {
    path: 'atendimento/:id/edit',
    component: AtendimentoComponent
  },
  {
    path: 'atendimento/:id',
    component: AtendimentoComponent
  },

  {
    path: 'cadastros',
    component: CadastrosComponent
  },
  {
    path: 'cadastros/new',
    component: CadastrosComponent
  },
  {
    path: 'cadastros/:id/edit',
    component: CadastrosComponent
  },
  {
    path: 'cadastros/:id',
    component: CadastrosComponent
  },

  {
    path: 'consultas',
    component: ConsultasComponent
  },
  {
    path: 'consultas/new',
    component: ConsultasComponent
  },
  {
    path: 'consultas/:id/edit',
    component: ConsultasComponent
  },
  {
    path: 'consultas/:id',
    component: ConsultasComponent
  },

  {
    path: 'pacientes',
    component: PacientesComponent
  },
  {
    path: 'pacientes/new',
    component: PacientesComponent
  },
  {
    path: 'pacientes/:id/edit',
    component: PacientesComponent
  },
  {
    path: 'pacientes/:id',
    component: PacientesComponent
  },
];
