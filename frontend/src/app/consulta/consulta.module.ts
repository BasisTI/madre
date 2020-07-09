import { ConsultaCalendarioComponent } from './components/consulta-calendario/consulta-calendario.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { RouterModule } from '@angular/router';
import { routes } from './consulta.routes';
import { ListarConsultasComponent } from './components/listar-consultas/listar-consultas.component';
import { EmergenciaComponent } from './components/emergencia/emergencia.component';

@NgModule({
    declarations: [EmergenciaComponent, ListarConsultasComponent, ConsultaCalendarioComponent],
    imports: [CommonModule, SharedModule, RouterModule.forChild(routes)],
})
export class ConsultaModule {}
