import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AgendamentoComponent } from './agendamento/agendamento.component';
import { AtendimentoComponent } from './atendimento/atendimento.component';
import { CadastrosComponent } from './cadastros/cadastros.component';
import { ConsultasComponent } from './consultas/consultas.component';
import { PacientesComponent } from './pacientes/pacientes.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    AgendamentoComponent,
    AtendimentoComponent,
    CadastrosComponent,
    ConsultasComponent,
    PacientesComponent,
  ]
})
export class AmbulatorioModule { }
