import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { routes } from './exames.routes';
import {ExamesComponent} from "./Components/exames/exames.component";
import {SharedModule} from "@shared/shared.module";
import {RouterModule} from "@angular/router";

import {TabViewModule} from 'primeng/tabview';
import {RadioButtonModule} from 'primeng/radiobutton';
import {CheckboxModule} from 'primeng/checkbox';


@NgModule({
  declarations: [
      ExamesComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(routes),
    TabViewModule,
    RadioButtonModule,
    CheckboxModule,
  ],
})
export class ExamesModule { }
