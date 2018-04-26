import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { PreCadastroComponent } from './pre-cadastro.component';
import { PreCadastroDetailComponent } from './pre-cadastro-detail.component';
import { PreCadastroFormComponent } from './pre-cadastro-form.component';

export const preCadastroRoute: Routes = [
  {
    path: '',
    component: PreCadastroComponent
  },
  {
    path: 'new',
    component: PreCadastroFormComponent
  },
  {
    path: ':id/edit',
    component: PreCadastroFormComponent
  },
  {
    path: ':id',
    component: PreCadastroDetailComponent
  },
];
