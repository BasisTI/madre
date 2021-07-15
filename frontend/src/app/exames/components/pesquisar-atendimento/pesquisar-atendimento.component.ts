import { Component, OnInit } from '@angular/core';
import { OPCOES_DE_ORIGEM } from '../../models/dropdowns/opcoes-de-origem';

@Component({
  selector: 'app-pesquisar-atendimento',
  templateUrl: './pesquisar-atendimento.component.html',
  styleUrls: ['./pesquisar-atendimento.component.css']
})
export class PesquisarAtendimentoComponent implements OnInit {

  constructor() { }

  opcoesDeOrigem = OPCOES_DE_ORIGEM;


  ngOnInit(): void {
  }

}
