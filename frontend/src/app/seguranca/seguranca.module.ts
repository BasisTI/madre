import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { SharedModule } from '@shared/shared.module';
import { RouterModule } from '@angular/router';
import { ServidoresComponent } from "./components/servidores/servidores.component";
import { routes } from "./seguranca.routes";
import { FormularioServidorComponent } from './components/servidores/formulario-servidor/formulario-servidor.component';
import { VinculosComponent } from './components/vinculos/vinculos.component';
import { OcupacoesDeCargoComponent } from './components/ocupacoes-de-cargo/ocupacoes-de-cargo.component';
import { CargosComponent } from './components/cargos/cargos.component';
import { GraduacoesDeServidoresComponent } from './components/graduacoes-de-servidores/graduacoes-de-servidores.component';
import { FormularioGraduacoesComponent } from './components/graduacoes-de-servidores/formulario-graduacoes/formulario-graduacoes.component';


@NgModule({
    declarations: [
        ServidoresComponent,
        FormularioServidorComponent,
        VinculosComponent,
        OcupacoesDeCargoComponent,
        CargosComponent,
        GraduacoesDeServidoresComponent,
        FormularioGraduacoesComponent,
    ],
    imports: [CommonModule, SharedModule, RouterModule.forChild(routes)],
})
export class SegurancaModule { }