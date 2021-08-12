import { Routes } from '@angular/router';
import { OcupacoesDeCargoComponent } from './components/ocupacoes-de-cargo/ocupacoes-de-cargo.component';
import { FormularioServidorComponent } from './components/servidores/formulario-servidor/formulario-servidor.component';
import { ServidoresComponent } from './components/servidores/servidores.component';
import { VinculosComponent } from './components/vinculos/vinculos.component';

export const routes: Routes = [
    { path: 'servidores', component: ServidoresComponent },
    { path: 'formulario', component: FormularioServidorComponent },
    { path: 'vinculos', component: VinculosComponent },
    { path: 'ocupacoes-de-cargo', component: OcupacoesDeCargoComponent },

];