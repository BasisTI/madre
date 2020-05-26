import { AutoCompleteModule } from 'primeng/autocomplete';
import { HttpClientModule } from '@angular/common/http';
import { SharedModule } from './../../shared/shared.module';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ProcedimentoEspecialRoutes } from './procedimento-especial.routes';
import { DiversosComponent } from './tipo-procedimento/diversos/diversos.component';

@NgModule({
    imports: [
        ProcedimentoEspecialRoutes,
        SharedModule,
        HttpClientModule,
        AutoCompleteModule

    ],
    declarations: [
        DiversosComponent
    ],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class ProcedimentoEspecialModule { }
