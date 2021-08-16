import { Routes } from '@angular/router';
import { PesquisarAtendimentoComponent } from './components/pesquisar-atendimento/pesquisar-atendimento.component';
import { SolicitarExameComponent } from './components/solicitar-exame/solicitar-exame.component';
import {AtendimentoDiversoComponent} from "./components/atendimento-diverso/atendimento-diverso.component";
import { FormularioExameComponent } from './components/formulario-exame/formulario-exame.component';
import { ListarSolicitacoesComponent } from './components/listar-solicitacoes/listar-solicitacoes.component';


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
        path: 'listar-solicitacoes',
        component: ListarSolicitacoesComponent,
    }
];
