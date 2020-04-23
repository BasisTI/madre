import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PrescricaoMedicaDietaService } from './prescricao-medica-dieta.service';
import { HttpClientModule } from '@angular/common/http';
import { BreadcrumbService } from './../../breadcrumb/breadcrumb.service';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { SharedModule } from './../../shared/shared.module';

import { PrescricaoMedicaDietaComponent } from './../dieta/prescricao-medica-dieta.component';
import {PrescricaoMedicaDietaRoutes} from './../dieta/prescricao-medica-dieta.routes';

@NgModule({
    imports: [
        PrescricaoMedicaDietaRoutes,
        SharedModule,
        BreadcrumbService,
        HttpClientModule,
    ],
    declarations: [
        PrescricaoMedicaDietaComponent,
        PrescricaoMedicaDietaService

    ],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class PrescricaoMedicaDietaModule {}
