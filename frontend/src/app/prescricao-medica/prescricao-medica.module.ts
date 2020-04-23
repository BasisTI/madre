import { HttpClientModule } from '@angular/common/http';
import { PrescricaoMedicaDietaComponent } from './dieta/prescricao-medica-dieta.component';
import { RouterModule } from '@angular/router';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SharedModule } from './../shared/shared.module';

import { PrescricaoMedicaComponent } from './prescricao-medica.component';
import { prescricaoMedica } from './prescricao-medica.router';
import { PrescricaoMedicaService } from './prescricao-medica.service';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';



@NgModule({
    imports: [
        SharedModule,
        RouterModule.forChild(prescricaoMedica),
        HttpClientModule,
        ReactiveFormsModule
    ],
    declarations: [
        PrescricaoMedicaComponent,
        PrescricaoMedicaDietaComponent
    ],
    providers: [PrescricaoMedicaService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class PrescricaoMedicaModule {}
