import { AutoCompleteModule } from 'primeng/autocomplete';
import { HttpClientModule } from '@angular/common/http';
import { SharedModule } from './../../shared/shared.module';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ProcedimentoEspecialRoutes } from './procedimento-especial.routes';
import { DiversosComponent } from './tipo-procedimento/diversos/diversos.component';
import { CirurgiasLeitoComponent } from './tipo-procedimento/cirurgias-leito/cirurgias-leito.component';
import { OrteseProteseComponent } from './tipo-procedimento/ortese-protese/ortese-protese.component';

@NgModule({
    imports: [ProcedimentoEspecialRoutes, SharedModule, HttpClientModule, AutoCompleteModule],
    declarations: [DiversosComponent, CirurgiasLeitoComponent, OrteseProteseComponent],
    providers: [],

    exports: [DiversosComponent, CirurgiasLeitoComponent, OrteseProteseComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class ProcedimentoEspecialModule {}
