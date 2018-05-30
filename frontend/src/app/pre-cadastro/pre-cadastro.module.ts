import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpModule } from '@angular/http';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DatatableModule } from '@basis/angular-components';
import { BotoesExportacaoModule } from './../botoes-exportacao/botoes-exportacao.module';
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
  PreCadastroComponent,
  PreCadastroDetailComponent,
  PreCadastroFormComponent,
  preCadastroRoute
} from './';
import { BotoesExportacaoComponent } from '../botoes-exportacao/botoes-exportacao.component';
import {NgxMaskModule} from 'ngx-mask';
import { UtilModule } from '../util/util.module';

@NgModule({
  imports: [
    CommonModule,
    HttpModule,
    FormsModule,
    RouterModule.forChild(preCadastroRoute),
    DatatableModule,
    ButtonModule,
    SpinnerModule,
    CalendarModule,
    DropdownModule,
    RadioButtonModule,
    InputTextModule,
    ConfirmDialogModule,
    BotoesExportacaoModule,
    UtilModule,
    NgxMaskModule.forRoot(),
  ],
  declarations: [
    PreCadastroComponent,
    PreCadastroDetailComponent,
    PreCadastroFormComponent
  ],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CadastrosbasicosPreCadastroModule {}
