import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UnidadeHospitalarComponent } from './unidade-hospitalar.component';
import { UnidadeHospitalarDetailComponent } from './unidade-hospitalar-detail.component';
import { UnidadeHospitalarFormComponent } from './unidade-hospitalar-form.component';

export const unidadeHospitalarRoute: Routes = [
  {
    path: '',
    component: UnidadeHospitalarComponent
  },
  {
    path: 'new',
    component: UnidadeHospitalarFormComponent
  },
  {
    path: ':id/edit',
    component: UnidadeHospitalarFormComponent
  },
  {
    path: ':id',
    component: UnidadeHospitalarDetailComponent
  },
];
