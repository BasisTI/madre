import { DarAltaAoPacienteComponent } from './components/dar-alta-ao-paciente/dar-alta-ao-paciente.component';
import { ListarDarAltaPacientesComponent } from './components/listar-dar-alta-pacientes/listar-dar-alta-pacientes.component';
import { FormularioDarAltaAoPacienteComponent } from './formulario-dar-alta-ao-paciente/formulario-dar-alta-ao-paciente.component';
import { TipoDeUnidadeFuncionalComponent } from './components/tipo-de-unidade-funcional/tipo-de-unidade-funcional.component';
import { ClinicaComponent } from './cadastro-clinicas/clinica/clinica.component';
import { CadastroUnidadesComponent } from './formulario-unidades/components/cadastro-unidades/cadastro-unidades.component';
import { BloqueioDeLeitoComponent } from './components/bloqueio-de-leito/bloqueio-de-leito.component';
import { CalendarioComponent } from '@internacao/components/leito/calendario.component';
import { InternacaoDePacienteComponent } from './components/internacao-de-paciente/internacao-de-paciente.component';
import { LiberacaoLeitoComponent } from './components/liberacao-leito/liberacao-leito.component';
import { PacientesListaComponent } from './components/pacientes-lista/pacientes-lista.component';
import { ReservaDeLeitoComponent } from './components/reserva-de-leito/reserva-de-leito.component';
import { Routes } from '@angular/router';
import { SolicitacaoDeInternacaoComponent } from './components/solicitacao-de-internacao/solicitacao-de-internacao.component';
import { SolicitacoesDeInternacaoComponent } from './components/solicitacoes-de-internacao/solicitacoes-de-internacao.component';

export const routes: Routes = [
    {
        path: 'lista-de-pacientes',
        component: PacientesListaComponent,
    },
    {
        path: 'solicitacao-de-internacao/:id',
        component: SolicitacaoDeInternacaoComponent,
    },
    {
        path: 'solicitacoes-de-internacao',
        component: SolicitacoesDeInternacaoComponent,
    },
    {
        path: 'internacao-de-paciente/:id',
        component: InternacaoDePacienteComponent,
    },
    {
        path: 'reserva-de-leito',
        component: ReservaDeLeitoComponent,
    },
    {
        path: 'bloqueio-de-leito',
        component: BloqueioDeLeitoComponent,
    },
    {
        path: 'liberacao-de-leito',
        component: LiberacaoLeitoComponent,
    },
    {
        path: 'calendario-leito',
        component: CalendarioComponent,
    },
    {
        path: 'cadastro-unidades',
        component: CadastroUnidadesComponent,
    },
    {
        path: 'cadastro-clinicas',
        component: ClinicaComponent
    },
    {
        path: 'tipo-unidade-funcional',
        component: TipoDeUnidadeFuncionalComponent,
    },
    {
        path: 'listar-dar-alta-ao-paciente',
        component: ListarDarAltaPacientesComponent,
    },
    {
        path:'formulario-dar-alta-ao-paciente',
        component: FormularioDarAltaAoPacienteComponent,
    },
    {
        path:'manter-dar-alta-ao-paciente',
        component: DarAltaAoPacienteComponent,
    }
];
