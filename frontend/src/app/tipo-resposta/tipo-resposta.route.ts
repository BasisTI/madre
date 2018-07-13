import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { TipoRespostaComponent } from './tipo-resposta.component';
import { TipoRespostaDetailComponent } from './tipo-resposta-detail.component';
import { TipoRespostaFormComponent } from './tipo-resposta-form.component';

export const tipoRespostaRoute: Routes = [
  {
    path: '',
    component: TipoRespostaComponent
  },
  {
    path: 'new',
    component: TipoRespostaFormComponent
  },
  {
    path: ':id/edit',
    component: TipoRespostaFormComponent
  },
  {
    path: ':id',
    component: TipoRespostaDetailComponent
  },
];
