import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpModule } from '@angular/http';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { DatatableModule } from '@basis/angular-components';
import { BotoesExportacaoModule } from "../botoes-exportacao/botoes-exportacao.module";
import {PickListModule} from 'primeng/picklist';
import {
  ButtonModule,
  InputTextModule,
  SpinnerModule,
  CalendarModule,
  DropdownModule,
  RadioButtonModule,
  ConfirmDialogModule,
  CheckboxModule
} from 'primeng/primeng';

import {
  PerfilComponent,
  PerfilDetailComponent,
  PerfilFormComponent,
  perfilRoute
} from './';

@NgModule({
  imports: [
    CommonModule,
    HttpModule,
    FormsModule,
    RouterModule.forChild(perfilRoute),
    DatatableModule,
    ButtonModule,
    SpinnerModule,
    CalendarModule,
    DropdownModule,
    RadioButtonModule,
    InputTextModule,
    ConfirmDialogModule,
    BotoesExportacaoModule,
    CheckboxModule,
    PickListModule,
  ],
  declarations: [
    PerfilComponent,
    PerfilDetailComponent,
    PerfilFormComponent
  ],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CadastrosbasicosPerfilModule {}
