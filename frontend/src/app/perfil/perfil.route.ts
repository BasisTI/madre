import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { PerfilComponent } from './perfil.component';
import { PerfilDetailComponent } from './perfil-detail.component';
import { PerfilFormComponent } from './perfil-form.component';

export const perfilRoute: Routes = [
  {
    path: '',
    component: PerfilComponent
  },
  {
    path: 'new',
    component: PerfilFormComponent
  },
  {
    path: ':id/edit',
    component: PerfilFormComponent
  },
  {
    path: ':id',
    component: PerfilDetailComponent
  },
];
