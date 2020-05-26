import { medicamentoRoute } from './medicamento/medicamento.routes';
import { PrescricaoMedicaDietaModule } from './dieta/prescricao-medica-dieta.module';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SharedModule } from './../shared/shared.module';

import { PrescricaoMedicaComponent } from './prescricao-medica.component';
import { prescricaoMedica } from './prescricao-medica.router';
import { PrescricaoMedicaService } from './prescricao-medica.service';
import { ReactiveFormsModule } from '@angular/forms';
import { MedicamentoComponent } from './medicamento/medicamento.component';
import { ProcedimentoEspecialComponent } from './procedimento-especial/procedimento-especial.component';
import { procedimentoEspecialRoute } from './procedimento-especial/procedimento-especial.routes';



@NgModule({
    imports: [
        SharedModule,
        RouterModule.forChild(prescricaoMedica),
        RouterModule.forChild(medicamentoRoute),
        RouterModule.forChild(procedimentoEspecialRoute),
        HttpClientModule,
        ReactiveFormsModule,
        PrescricaoMedicaDietaModule,
    ],
    declarations: [
        PrescricaoMedicaComponent,
        MedicamentoComponent,
        ProcedimentoEspecialComponent,
    ],
    providers: [PrescricaoMedicaService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class PrescricaoMedicaModule {}
