import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { PacienteComponent } from './paciente.component';
import { PacienteDetailComponent } from './paciente-detail.component';
import { PacienteFormComponent } from './paciente-form.component';

export const pacienteRoute: Routes = [
  {
    path: '',
    component: PacienteComponent
  },
  {
    path: 'new',
    component: PacienteFormComponent
  },
  {
    path: ':id/edit',
    component: PacienteFormComponent
  },
  {
    path: ':id',
    component: PacienteDetailComponent
  },
];
