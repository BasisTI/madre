import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpModule } from '@angular/http';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { DatatableModule } from '@basis/angular-components';
import { BotoesExportacaoModule } from './../botoes-exportacao/botoes-exportacao.module';
import {
  ButtonModule,
  InputTextModule,
  SpinnerModule,
  CalendarModule,
  DropdownModule,
  RadioButtonModule,
  ConfirmDialogModule,
  ListboxModule,
  MultiSelectModule,
} from 'primeng/primeng';

import {
  UsuarioComponent,
  UsuarioDetailComponent,
  UsuarioFormComponent,
  usuarioRoute
} from './';

import {BlockUIModule} from 'ng-block-ui';
import { MessageService } from 'primeng/components/common/messageservice';
import {ToastrModule} from 'ngx-toastr';

@NgModule({
  imports: [
    CommonModule,
    HttpModule,
    FormsModule,
    RouterModule.forChild(usuarioRoute),
    DatatableModule,
    ButtonModule,
    SpinnerModule,
    CalendarModule,
    DropdownModule,
    RadioButtonModule,
    InputTextModule,
    ListboxModule,
    MultiSelectModule,
    ConfirmDialogModule,
    BotoesExportacaoModule
  ],
  declarations: [
    UsuarioComponent,
    UsuarioDetailComponent,
    UsuarioFormComponent
  ],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CadastrosbasicosUsuarioModule {}
