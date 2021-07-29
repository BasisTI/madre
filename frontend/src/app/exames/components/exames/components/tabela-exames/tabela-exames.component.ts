import { Component, Input, OnInit } from '@angular/core';
import { ExamModel } from 'src/app/exames/models/subjects/exames-model';
import { ItemSolicitacaoExame } from 'src/app/exames/models/subjects/item-solicitacao-exame';

@Component({
  selector: 'app-tabela-exames',
  templateUrl: './tabela-exames.component.html',
  styleUrls: ['./tabela-exames.component.css']
})
export class TabelaExamesComponent implements OnInit {

  
  @Input()
  itemsRecebidos: ItemSolicitacaoExame;

  constructor() { }

  ngOnInit(): void {
  }

  // método

}
