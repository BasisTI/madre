import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { routesExam } from './exames.routes';
import { SharedModule } from "@shared/shared.module";
import { RouterModule } from "@angular/router";

import { ExamesComponent } from "./Components/exames/exames.component";
import { SolicitarExameComponent } from './views/solicitar-exame/solicitar-exame.component';


@NgModule({
  declarations: [
      ExamesComponent,
      SolicitarExameComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(routesExam),
  ],
})

export class ExamesModule { }
