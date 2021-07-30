import { ViewChild } from '@angular/core';
import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DatatableClickEvent, DatatableComponent } from '@nuvem/primeng-components';
import { ItemSolicitacaoExame } from 'src/app/exames/models/subjects/item-solicitacao-exame';
import { ExamesService } from 'src/app/exames/services/exames.service';
import { ItemSolicitacaoExameService } from 'src/app/exames/services/item-solicitacao-exame.service';

@Component({
  selector: 'app-tabela-exames',
  templateUrl: './tabela-exames.component.html',
  styleUrls: ['./tabela-exames.component.css']
})
export class TabelaExamesComponent implements OnInit {

  @Input()
  itemsRecebidos: ItemSolicitacaoExame[];

  itemsTratados: any[];
  itemSolicitacao: any;

  constructor(private examesService: ExamesService,
              private router: Router,) { }

  ngOnInit(): void {
    this.itemsRecebidos.forEach((element) => {

      this.examesService.GetExamesPorId(element.itemSolicitacaoExameId).subscribe((res) => {

        this.itemSolicitacao = {
          urgente: element.urgente,
          situacao: element.situacao,
          dataProgramada: element.dataProgramada,
          exame: res,
        }

        console.log(this.itemSolicitacao)
      })
    });

  }

  datatableClick(event: DatatableClickEvent) {
}
 
}
