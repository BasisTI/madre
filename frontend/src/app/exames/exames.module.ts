import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { routesExam } from './exames.routes';
import { SharedModule } from "@shared/shared.module";
import { RouterModule } from "@angular/router";

import { ExamesComponent } from "./Components/exames/exames.component";


@NgModule({
  declarations: [
      ExamesComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(routesExam),
  ],
})

export class ExamesModule { }
