import { Component, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { ItemSolicitacaoExame } from 'src/app/exames/models/subjects/item-solicitacao-exame';
import { ExamesService } from 'src/app/exames/services/exames.service';
// import { ItemSolicitacaoExameService } from 'src/app/exames/services/item-solicitacao-exame.service';

@Component({
  selector: 'app-tabela-exames',
  templateUrl: './tabela-exames.component.html',
  styleUrls: ['./tabela-exames.component.css']
})
export class TabelaExamesComponent implements OnInit, OnChanges {

  @Input()
  itemsRecebidos: ItemSolicitacaoExame[] = [];

  @Input()
  flag: boolean;

  itemsTratados: any[] = [];
  itemSolicitacao: any;
  index: number = 1

  constructor(private examesService: ExamesService) { }

  ngOnChanges(changes: SimpleChanges) {
    this.salvarItems();
  }

  ngOnInit(): void {
  }

  salvarItems() {
    this.itemsRecebidos.forEach((element) => {

      this.examesService.GetExamesPorId(element.itemSolicitacaoExameId).subscribe((res) => {

        this.itemSolicitacao = {
          urgente: element.urgente,
          situacao: element.situacao,
          dataProgramada: element.dataProgramada,
          exame: res,
          index: this.index
        }

        this.index++;
        this.itemsTratados.push(this.itemSolicitacao)
      })
    })
  }

  teste() {
    console.log(this.itemsRecebidos)
    this.salvarItems()
  }

  removerItem(index: number) {
    this.itemsTratados = this.itemsTratados.filter((item) => (item.index != index));
  }

  pegarItemsTratados(): any[] {
    return this.itemsTratados;
  }

}
