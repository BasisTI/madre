import { Component, OnInit } from '@angular/core';
import { SituationDropdown } from "../../models/dropdowns/situation.dropdown";
import { ExamModel } from '../../models/subjects/exames-model';
import { GruposExamesService } from '../../services/grupos-exames.service';
import { ExamesService } from '../../services/exames.service';
import { GrupoModel } from '../../models/subjects/grupo-model';
import { FormBuilder, Validators } from '@angular/forms';
import { ItemSolicitacaoExame } from '../../models/subjects/item-solicitacao-exame';
import { ItemSolicitacaoExameService } from '../../services/item-solicitacao-exame.service';
// import { AutoCompleteDemo } from '../../models/dropdowns/exams.dropdown';

@Component({
  selector: 'app-exames',
  templateUrl: './exames.component.html',
  styleUrls: ['./exames.component.scss']
})
export class ExamesComponent implements OnInit {

  exames: ExamModel[] = [];
  examesSelecionados: ExamModel[] = [];

  // Por exame:
  situationDropdown = SituationDropdown;
  selectedValue: String;
  unitCheckbox: Boolean;
  selectedExam: ExamModel;
  exameNome: string;
  urgente: Boolean;
  date: Date;
  selectedSituation: String;

  // Por lote:
  selectedLote: String;
  group: String = null;
  groups: GrupoModel[];


  constructor(
    private gruposExamesService: GruposExamesService,
    private examesService: ExamesService,
    private fb: FormBuilder,
    private itemSolicitacaoExameService: ItemSolicitacaoExameService
    ) { }


    itemSolicitacao = this.fb.group({
      exameId: [null, Validators.required],
      urgente: [null, Validators.required],
      dataProgramada: [null, Validators.required],
      situacao: [null, Validators.required]
    });



  ngOnInit(): void{
    this.gruposExamesService.GetGrupos().subscribe((response) => {
      this.groups = response;
    })
    this.examesService.GetExames().subscribe((response) => {
      this.exames = response;
    })

  }

  receberExamesSelecionados(event) {
    this.examesSelecionados = event;
  }
  
  cadastrar() {
    let itemExame = this.itemSolicitacao.value;

    let item: ItemSolicitacaoExame = {
      itemSolicitacaoExameId: itemExame.itemSolicitacaoExameId,
      urgente: itemExame.urgente,
      dataProgramada: itemExame.dataProgramada,
      situacao: itemExame.situacao
    }

    this.itemSolicitacaoExameService.adicionarItemExame(item).subscribe();
  }
}
