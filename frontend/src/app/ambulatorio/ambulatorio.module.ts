import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AgendamentoComponent } from './agendamento/agendamento.component';
import { AtendimentoComponent } from './atendimento/atendimento.component';
import { CadastrosComponent } from './cadastros/cadastros.component';
import { ConsultasComponent } from './consultas/consultas.component';
import { PacientesComponent } from './pacientes/pacientes.component';
import { HttpModule } from '@angular/http';
import { RouterModule } from '@angular/router';
import { AmbulatorioRoute } from './ambulatorio.route';
import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    HttpModule,
    FormsModule,
    RouterModule.forChild(AmbulatorioRoute)
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
