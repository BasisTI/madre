import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { FuncionalidadeComponent } from './funcionalidade.component';
import { FuncionalidadeDetailComponent } from './funcionalidade-detail.component';
import { FuncionalidadeFormComponent } from './funcionalidade-form.component';

export const funcionalidadeRoute: Routes = [
  {
    path: '',
    component: FuncionalidadeComponent
  },
  {
    path: 'new',
    component: FuncionalidadeFormComponent
  },
  {
    path: ':id/edit',
    component: FuncionalidadeFormComponent
  },
  {
    path: ':id',
    component: FuncionalidadeDetailComponent
  },
];
