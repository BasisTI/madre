import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { SituationDropdown } from 'src/app/exames/models/dropdowns/situation.dropdown';
import { ItemSolicitacaoExame } from 'src/app/exames/models/subjects/item-solicitacao-exame';
import { ItemSolicitacaoExameService } from 'src/app/exames/services/item-solicitacao-exame.service';

@Component({
  selector: 'app-situacao-exame',
  templateUrl: './situacao-exame.component.html',
  styleUrls: ['./situacao-exame.component.css']
})
export class SituacaoExameComponent implements OnInit {

  urgentCheck: boolean = false;
  date: Date;
  situationDropdown = SituationDropdown;
  selectedSituation: string;

  itemSolicitacao = this.fb.group({
    exameId: [null, Validators.required],
    urgente: [null, Validators.required],
    dataProgramada: [null, Validators.required],
    situacao: [null, Validators.required]
  });

  constructor(private fb: FormBuilder, private itemSolicitacaoExameService: ItemSolicitacaoExameService) { }

  ngOnInit(): void {
  }

  cadastrar() {
    let itemExame = this.itemSolicitacao.value;

    let item: ItemSolicitacaoExame = {
      itemSolicitacaoExameId: itemExame.itemSolicitacaoExameId ? itemExame.itemSolicitacaoExameId.id: null,
      urgente: itemExame.urgente,
      dataProgramada: itemExame.dataProgramada,
      situacao: itemExame.situacao
    }

    this.itemSolicitacaoExameService.adicionarItemExame(item).subscribe();
  }

}
