import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpModule } from '@angular/http';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { DatatableModule } from '@basis/angular-components';
import {
  ButtonModule,
  InputTextModule,
  SpinnerModule,
  CalendarModule,
  DropdownModule,
  RadioButtonModule,
  ConfirmDialogModule
} from 'primeng/primeng';

import {
  Funcionalidade_acaoComponent,
  Funcionalidade_acaoDetailComponent,
  Funcionalidade_acaoFormComponent,
  funcionalidade_acaoRoute
} from './';

@NgModule({
  imports: [
    CommonModule,
    HttpModule,
    FormsModule,
    RouterModule.forChild(funcionalidade_acaoRoute),
    DatatableModule,
    ButtonModule,
    SpinnerModule,
    CalendarModule,
    DropdownModule,
    RadioButtonModule,
    InputTextModule,
    ConfirmDialogModule,
  ],
  declarations: [
    Funcionalidade_acaoComponent,
    Funcionalidade_acaoDetailComponent,
    Funcionalidade_acaoFormComponent
  ],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CadastrosbasicosFuncionalidade_acaoModule {}
