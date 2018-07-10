import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { TriagemComponent } from './triagem.component';
import { TriagemDetailComponent } from './triagem-detail.component';
import { TriagemFormComponent } from './triagem-form.component';

export const triagemRoute: Routes = [
  {
    path: '',
    component: TriagemComponent
  },
  {
    path: 'new',
    component: TriagemFormComponent
  },
  {
    path: ':id/edit',
    component: TriagemFormComponent
  },
  {
    path: ':id',
    component: TriagemDetailComponent
  },
];
