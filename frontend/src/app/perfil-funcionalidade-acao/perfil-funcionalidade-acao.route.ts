import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { Perfil_funcionalidade_acaoComponent } from './perfil-funcionalidade-acao.component';
import { Perfil_funcionalidade_acaoDetailComponent } from './perfil-funcionalidade-acao-detail.component';
import { Perfil_funcionalidade_acaoFormComponent } from './perfil-funcionalidade-acao-form.component';

export const perfil_funcionalidade_acaoRoute: Routes = [
  {
    path: '',
    component: Perfil_funcionalidade_acaoComponent
  },
  {
    path: 'new',
    component: Perfil_funcionalidade_acaoFormComponent
  },
  {
    path: ':id/edit',
    component: Perfil_funcionalidade_acaoFormComponent
  },
  {
    path: ':id',
    component: Perfil_funcionalidade_acaoDetailComponent
  },
];
