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

  // Por exame:
  situationDropdown = SituationDropdown;
  selectedValue: String;
  unitCheckbox: Boolean;
  selectedExam: ExamModel;
  exameNome: string;
  urgente: Boolean;
  date: Date;
  selectedSituation: String;
  // public exames = new Array<ExamModel>();
  // options = AutoCompleteDemo;

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
      urgente: [null, Validators.required],
      dataProgramada: [null, Validators.required],
      situacao: [null, Validators.required]
    });


  handleClick() {
    console.log("For exam Data");
    console.log(this.selectedValue);
    console.log(this.unitCheckbox);
    console.log(this.selectedExam);
    console.log(this.urgente);
    console.log(this.date);
    console.log(this.selectedSituation);

    
    console.log("For Lote Data");
    console.log(this.selectedLote);
    console.log(this.group);
  }

  listarExames(){ }

  aoSelecionarExame(){ }


  ngOnInit(): void{
    this.gruposExamesService.GetGrupos().subscribe((response) => {
      this.groups = response;
    })
    this.examesService.GetExames().subscribe((response) => {
      this.exames = response;
    })

  }

  // Métodos

  search(texto: string) {
    this.examesService.GetExames().subscribe(data => {
        this.exames = data.filter((elem)=> elem.nome.includes(texto))
    });
  }

  selecionarExame(event) {
    this.selectedExam = event.query;

    this.exameNome = event.query.nome;

  }
  
  cadastrar() {
    let itemExame = this.itemSolicitacao.value;

    let item: ItemSolicitacaoExame = {
      urgente: itemExame.urgente,
      dataProgramada: itemExame.dataProgramada,
      situacao: itemExame.situacao
    }

    this.itemSolicitacaoExameService.adicionarItemExame(item).subscribe();
  }
}
