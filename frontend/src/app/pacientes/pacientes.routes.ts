import { Routes } from '@angular/router';

import { FormularioCadastroComponent } from './formulario-paciente/formulario-cadastro.component';
import { ListaDePacientes } from './lista-de-pacientes.component';

export const routes: Routes = [
  { path: '', component: ListaDePacientes },
  { path: 'cadastro', component: FormularioCadastroComponent },
];
