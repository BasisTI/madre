import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { crudRouteBuilder } from '@nuvem/primeng-components';

import { PacienteComponent } from './paciente.component';
import { PacienteFormComponent } from './paciente-form.component';
import { PacienteListComponent } from './paciente-list.component';

@NgModule({
  imports: [
    RouterModule.forChild([
      crudRouteBuilder("paciente", PacienteComponent, PacienteListComponent, PacienteFormComponent)
    ])
  ],
  exports: [
    RouterModule
  ]
})
export class PacienteRoutingModule { }