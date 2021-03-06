import { Routes } from '@angular/router';
import { PesquisarAtendimentoComponent } from './components/pesquisar-atendimento/pesquisar-atendimento.component';
import { SolicitarExameComponent } from './components/solicitar-exame/solicitar-exame.component';
import {AtendimentoDiversoComponent} from "./components/atendimento-diverso/atendimento-diverso.component";


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
    }
];
