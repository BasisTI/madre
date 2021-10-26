import { HttpClientModule } from '@angular/common/http';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { SharedModule } from './../../shared/shared.module';

import { PrescricaoMedicaDietaComponent } from './../dieta/prescricao-medica-dieta.component';
import { PrescricaoMedicaDietaRoutes } from './../dieta/prescricao-medica-dieta.routes';

@NgModule({
    imports: [PrescricaoMedicaDietaRoutes, SharedModule, HttpClientModule],
    declarations: [PrescricaoMedicaDietaComponent],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class PrescricaoMedicaDietaModule {}
