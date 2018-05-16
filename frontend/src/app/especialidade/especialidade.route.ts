import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { EspecialidadeComponent } from './especialidade.component';
import { EspecialidadeDetailComponent } from './especialidade-detail.component';
import { EspecialidadeFormComponent } from './especialidade-form.component';

export const especialidadeRoute: Routes = [
  {
    path: '',
    component: EspecialidadeComponent
  },
  {
    path: 'new',
    component: EspecialidadeFormComponent
  },
  {
    path: ':id/edit',
    component: EspecialidadeFormComponent
  },
  {
    path: ':id',
    component: EspecialidadeDetailComponent
  },
];
