import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { AnexoComponent } from './anexo.component';
import { AnexoDetailComponent } from './anexo-detail.component';
import { AnexoFormComponent } from './anexo-form.component';

export const anexoRoute: Routes = [
  {
    path: '',
    component: AnexoComponent
  },
  {
    path: 'new',
    component: AnexoFormComponent
  },
  {
    path: ':id/edit',
    component: AnexoFormComponent
  },
  {
    path: ':id',
    component: AnexoDetailComponent
  },
];
