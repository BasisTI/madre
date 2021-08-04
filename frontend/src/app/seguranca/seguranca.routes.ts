import { Routes } from '@angular/router';
import { FormularioServidorComponent } from './components/servidores/formulario-servidor/formulario-servidor.component';
import { ServidoresComponent } from './components/servidores/servidores.component';

export const routes: Routes = [
    { path: 'servidores', component: ServidoresComponent },
    { path: 'formulario', component: FormularioServidorComponent },

];