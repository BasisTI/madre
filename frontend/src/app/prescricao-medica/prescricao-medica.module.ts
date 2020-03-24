import { PrescricaoMedicaDietaComponent } from './dieta/prescricao-medica-dieta.component';
import { RouterModule } from '@angular/router';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SharedModule } from './../shared/shared.module';

import { PrescricaoMedicaComponent } from './prescricao-medica.component';
import { prescricaoMedica } from './prescricao-medica.router';



@NgModule({
    imports: [
        SharedModule,
        RouterModule.forChild(prescricaoMedica)
    ],
    declarations: [
        PrescricaoMedicaComponent,
        PrescricaoMedicaDietaComponent
    ],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class PrescricaoMedicaModule {}
