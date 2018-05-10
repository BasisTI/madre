import { BotoesExportacaoComponent } from './botoes-exportacao.component';
import { SplitButtonModule } from 'primeng/primeng';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { PageNotificationService } from '@basis/angular-components';
import {PanelMenuModule} from 'primeng/panelmenu';

@NgModule({
  imports: [
    SplitButtonModule
  ],
  declarations: [
    BotoesExportacaoComponent
  ],
  providers: [],
  exports: [BotoesExportacaoComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BotoesExportacaoModule {
}
