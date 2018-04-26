import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UsuarioComponent } from './usuario.component';
import { UsuarioDetailComponent } from './usuario-detail.component';
import { UsuarioFormComponent } from './usuario-form.component';

export const usuarioRoute: Routes = [
  {
    path: '',
    component: UsuarioComponent
  },
  {
    path: 'new',
    component: UsuarioFormComponent
  },
  {
    path: ':id/edit',
    component: UsuarioFormComponent
  },
  {
    path: ':id',
    component: UsuarioDetailComponent
  },
];
