import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpModule } from '@angular/http';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { DatatableModule } from '@basis/angular-components';
import { BotoesExportacaoModule } from '../botoes-exportacao/botoes-exportacao.module';

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
  TipoRespostaComponent,
  TipoRespostaDetailComponent,
  TipoRespostaFormComponent,
  tipoRespostaRoute
} from './';

@NgModule({
  imports: [
    CommonModule,
    HttpModule,
    FormsModule,
    RouterModule.forChild(tipoRespostaRoute),
    DatatableModule,
    ButtonModule,
    SpinnerModule,
    CalendarModule,
    DropdownModule,
    RadioButtonModule,
    InputTextModule,
    BotoesExportacaoModule,
    ConfirmDialogModule,
  ],
  declarations: [
    TipoRespostaComponent,
    TipoRespostaDetailComponent,
    TipoRespostaFormComponent
  ],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CadastrosbasicosTipoRespostaModule {}
