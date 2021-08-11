import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { SharedModule } from '@shared/shared.module';
import { RouterModule } from '@angular/router';
import { ServidoresComponent } from "./components/servidores/servidores.component";
import { routes } from "./seguranca.routes";
import { FormularioServidorComponent } from './components/servidores/formulario-servidor/formulario-servidor.component';
import { VinculosComponent } from './components/vinculos/vinculos.component';


@NgModule({
    declarations: [
        ServidoresComponent,
        FormularioServidorComponent,
        VinculosComponent
    ],
    imports: [CommonModule, SharedModule, RouterModule.forChild(routes)],
})
export class SegurancaModule { }