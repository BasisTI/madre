import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { Funcionalidade_acaoComponent } from './funcionalidade-acao.component';
import { Funcionalidade_acaoDetailComponent } from './funcionalidade-acao-detail.component';
import { Funcionalidade_acaoFormComponent } from './funcionalidade-acao-form.component';

export const funcionalidade_acaoRoute: Routes = [
  {
    path: '',
    component: Funcionalidade_acaoComponent
  },
  {
    path: 'new',
    component: Funcionalidade_acaoFormComponent
  },
  {
    path: ':id/edit',
    component: Funcionalidade_acaoFormComponent
  },
  {
    path: ':id',
    component: Funcionalidade_acaoDetailComponent
  },
];
