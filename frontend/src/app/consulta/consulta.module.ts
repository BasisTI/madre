import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { RouterModule } from '@angular/router';
import { routes } from './consulta.routes';

import { ListarConsultasComponent } from './components/listar-consultas/listar-consultas.component';

@NgModule({
    declarations: [ListarConsultasComponent],
    imports: [CommonModule, SharedModule, RouterModule.forChild(routes)],
})
export class ConsultaModule {}
