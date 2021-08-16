import { Component, OnInit } from '@angular/core';
import { SolicitacaoExame } from '../../models/subjects/solicitacao-exame';
import { SolicitacaoExameService } from '../../services/solicitacao-exame.service';

@Component({
  selector: 'app-listar-solicitacoes',
  templateUrl: './listar-solicitacoes.component.html',
  styleUrls: ['./listar-solicitacoes.component.css']
})
export class ListarSolicitacoesComponent implements OnInit {

  id: string = '';
  pedidoPrimeiroExame: string = '';
  usoAntimicrobianos24h: string = ''
  situacao: string = '';
  infoClinica: string = '';
  results = [];
  solicitacao: SolicitacaoExame[];

  constructor(private solicitacaoService: SolicitacaoExameService) {
    this.results = [
      {label: 'Sim', value: 'true'},
      {label: 'Não', value: 'false'}
    ];
   }

  ngOnInit(): void {
    this.listar();
  }

  listar(){
    this.solicitacaoService
      .getSolicitacao(this.id, this.pedidoPrimeiroExame, this.usoAntimicrobianos24h)
      .subscribe((response) => {
        this.solicitacao = response;
      });
      this.situacao = '';
  }

  exportarSolicitacoes(){

  }

  limparPesquisa(){

  }

}
