import { MedicamentoRoutes } from './medicamento.routes';
import { MedicamentoComponent } from './medicamento.component';
import { HttpClientModule } from '@angular/common/http';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { SharedModule } from './../../shared/shared.module';


@NgModule({
    imports: [
        MedicamentoRoutes,
        SharedModule,
        HttpClientModule,
    ],
    declarations: [
        MedicamentoComponent,
    ],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class MedicamentoModule {}
