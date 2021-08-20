import { Routes } from '@angular/router';
import { PesquisarAtendimentoComponent } from './components/pesquisar-atendimento/pesquisar-atendimento.component';
import { SolicitarExameComponent } from './components/solicitar-exame/solicitar-exame.component';
import {AtendimentoDiversoComponent} from "./components/atendimento-diverso/atendimento-diverso.component";
import { FormularioExameComponent } from './components/formulario-exame/formulario-exame.component';
import { CadastrarMaterialComponent } from './components/cadastrar-material/cadastrar-material.component';
import { FormularioGradeDeAgendamentoComponent } from './components/formulario-grade-de-agendamento/formulario-grade-de-agendamento.component';
import { ListarGradeDeExameComponent } from './components/listar-grade-de-exame/listar-grade-de-exame.component';


export const routes: Routes = [
    {
        path: 'pesquisar-atendimento',
        component: PesquisarAtendimentoComponent,
    },
    {
        path: 'solicitar-exame',
        component: SolicitarExameComponent,
    },
    {
        path: 'atendimento-diverso',
        component: AtendimentoDiversoComponent,
    },
    {
        path: 'formulario-exame',
        component: FormularioExameComponent,
    },
    {
        path: 'cadastrar-material',
        component: CadastrarMaterialComponent,
    },
    {
        path: 'formulario-grade-agendamento',
        component: FormularioGradeDeAgendamentoComponent
    },
    {
        path: 'listar-grade-exame',
        component: ListarGradeDeExameComponent
    }
];
