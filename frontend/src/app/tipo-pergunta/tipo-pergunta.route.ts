import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { TipoPerguntaComponent } from './tipo-pergunta.component';
import { TipoPerguntaDetailComponent } from './tipo-pergunta-detail.component';
import { TipoPerguntaFormComponent } from './tipo-pergunta-form.component';

export const tipoPerguntaRoute: Routes = [
  {
    path: '',
    component: TipoPerguntaComponent
  },
  {
    path: 'new',
    component: TipoPerguntaFormComponent
  },
  {
    path: ':id/edit',
    component: TipoPerguntaFormComponent
  },
  {
    path: ':id',
    component: TipoPerguntaDetailComponent
  },
];
