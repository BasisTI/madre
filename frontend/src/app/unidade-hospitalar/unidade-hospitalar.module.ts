import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpModule } from '@angular/http';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { BotoesExportacaoModule } from './../botoes-exportacao/botoes-exportacao.module';
import { DatatableModule } from '@basis/angular-components';
import {
  ButtonModule,
  InputTextModule,
  SpinnerModule,
  CalendarModule,
  DropdownModule,
  RadioButtonModule,
  ConfirmDialogModule,
  FileUploadModule
} from 'primeng/primeng';

import {
  UnidadeHospitalarComponent,
  UnidadeHospitalarDetailComponent,
  UnidadeHospitalarFormComponent,
  unidadeHospitalarRoute,
  UnidadeHospitalarService
} from './';
import { UploadService } from '../upload/upload.service';
import {NgxMaskModule} from 'ngx-mask';
@NgModule({
  imports: [
    CommonModule,
    HttpModule,
    FormsModule,
    RouterModule.forChild(unidadeHospitalarRoute),
    DatatableModule,
    ButtonModule,
    SpinnerModule,
    CalendarModule,
    DropdownModule,
    RadioButtonModule,
    InputTextModule,
    ConfirmDialogModule,
    FileUploadModule,
    BotoesExportacaoModule,
    NgxMaskModule.forRoot()
  ],
  declarations: [
    UnidadeHospitalarComponent,
    UnidadeHospitalarDetailComponent,
    UnidadeHospitalarFormComponent
  ],
  providers: [
    UnidadeHospitalarService
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CadastrosbasicosUnidadeHospitalarModule {}
