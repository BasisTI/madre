import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { AcaoComponent } from './acao.component';
import { AcaoDetailComponent } from './acao-detail.component';
import { AcaoFormComponent } from './acao-form.component';

export const acaoRoute: Routes = [
  {
    path: '',
    component: AcaoComponent
  },
  {
    path: 'new',
    component: AcaoFormComponent
  },
  {
    path: ':id/edit',
    component: AcaoFormComponent
  },
  {
    path: ':id',
    component: AcaoDetailComponent
  },
];
