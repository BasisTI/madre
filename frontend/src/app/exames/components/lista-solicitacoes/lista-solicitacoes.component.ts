import { Component, OnInit } from '@angular/core';
import { SolicitacaoExame } from '../../models/subjects/solicitacao-exame';
import { SolicitacaoExameService } from '../../services/solicitacao-exame.service';
import * as moment from 'moment';

@Component({
  selector: 'app-lista-solicitacoes',
  templateUrl: './lista-solicitacoes.component.html',
  styleUrls: ['./lista-solicitacoes.component.css']
})
export class ListaSolicitacoesComponent implements OnInit {

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
    this.solicitacaoService.exportarSolicitacoes().subscribe(x => {
      const blob = new Blob([x], {type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'});

      if (window.navigator && window.navigator.msSaveOrOpenBlob) {
          window.navigator.msSaveOrOpenBlob(blob)
          return;
      }
      const data = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = data;
      let dataAtual: Date = new Date();
      let formattedDate = (moment( dataAtual)).format('DD-MM-YYYY_HH-mm-ss')
      let nomeArquivo: string = 'solicitacoes_' + formattedDate + '.xlsx';

      link.download = nomeArquivo;
      link.dispatchEvent(new MouseEvent('click', {bubbles: true, cancelable: true, view: window}))

      setTimeout(function() {
          window.URL.revokeObjectURL(data);
          link.remove
      }, 100)
  })

  }

  limparPesquisa(){

  }

}
