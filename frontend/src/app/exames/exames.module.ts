import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AtendimentoDiversoComponent } from "./components/atendimento-diverso/atendimento-diverso.component";
import { SharedModule } from "@shared/shared.module";
import { RouterModule } from "@angular/router";
import { routes } from './exames.routes';



@NgModule({
  declarations: [
    AtendimentoDiversoComponent
  ],
  imports: [
    CommonModule,
    SharedModule, 
    RouterModule.forChild(routes),
  ]
})
export class ExamesModule { }
