import { Routes } from '@angular/router';

import { ListaDePacientesComponent } from './lista-de-pacientes/lista-de-pacientes.component';
import { FormularioCadastroComponent } from './formulario-paciente/formulario-cadastro.component';

export const routes: Routes = [
    { path: '', component: ListaDePacientesComponent },
    { path: 'cadastro', component: FormularioCadastroComponent },
];
