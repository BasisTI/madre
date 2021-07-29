import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { SituationDropdown } from "../../models/dropdowns/situation.dropdown";
import { BreadcrumbService, CALENDAR_LOCALE, DatatableComponent } from '@nuvem/primeng-components';
import { ExamModel } from '../../models/subjects/exames-model';
import { GruposExamesService } from '../../services/grupos-exames.service';
import { ExamesService } from '../../services/exames.service';
import { GrupoModel } from '../../models/subjects/grupo-model';
import { FormBuilder, Validators } from '@angular/forms';
import { ItemSolicitacaoExame } from '../../models/subjects/item-solicitacao-exame';
import { ItemSolicitacaoExameService } from '../../services/item-solicitacao-exame.service';
import { ExameComponent } from './components/exame/exame.component';
// import { AutoCompleteDemo } from '../../models/dropdowns/exams.dropdown';

@Component({
  selector: 'app-exames',
  templateUrl: './exames.component.html',
  styleUrls: ['./exames.component.scss']
})
export class ExamesComponent implements OnInit {

  itemsSolicitacaoExame: ItemSolicitacaoExame[] =[]
  exames: ExamModel[] = [];
  examesSelecionados: ExamModel[] = [];
  itemsSolicitacaoIDs: number[];

  @ViewChild(ExameComponent) appExame: ExameComponent;

  // Por exame:
  situationDropdown = SituationDropdown;
  selectedExamID: number;

  // Por lote:
  group: String = null;
  groups: GrupoModel[];


  constructor(
    private breadcrumbService: BreadcrumbService,
    private gruposExamesService: GruposExamesService,
    private examesService: ExamesService,
    private fb: FormBuilder,
    private itemSolicitacaoExameService: ItemSolicitacaoExameService
    ) { }


    itemSolicitacaoPorExame = this.fb.group(
      {
        urgente: [false, Validators.required],
        dataProgramada: [new Date(), Validators.required],
        situacao: [null, Validators.required],
      }
    );

    itemSolicitacaoPorLote = this.fb.group(
      {
        urgente: [false, Validators.required],
        dataProgramada: [new Date(), Validators.required],
        situacao: [null, Validators.required],
      }
    );

  ngOnInit(): void{
    this.gruposExamesService.GetGrupos().subscribe((response) => {
      this.groups = response;
    })
    this.examesService.GetExames().subscribe((response) => {
      this.exames = response;
    })

  }

  // Métodos
  
  cadastrarPorExame() {

    let item: ItemSolicitacaoExame = {

      urgente: this.itemSolicitacaoPorExame.value.urgente,
      dataProgramada: this.itemSolicitacaoPorExame.value.dataProgramada,
      situacao: this.itemSolicitacaoPorExame.value.situacao,
      itemSolicitacaoExameId: this.selectedExamID,
    }
    
    this.itemsSolicitacaoExame.push(item);
    // this.itemSolicitacaoExameService.adicionarItemExame(item).subscribe();
  }

  cadastrarPorLote() {

    this.examesSelecionados = this.appExame.pegarExames();

    this.examesSelecionados.forEach((exame)=> {

      let item: ItemSolicitacaoExame = {

        urgente: this.itemSolicitacaoPorLote.value.urgente,
        dataProgramada: this.itemSolicitacaoPorLote.value.dataProgramada,
        situacao: this.itemSolicitacaoPorLote.value.situacao,
        itemSolicitacaoExameId: exame.id,

      }
      
      this.itemsSolicitacaoExame.push(item);
      // this.itemSolicitacaoExameService.adicionarItemExame(item).subscribe();
    })
  }

  salvarItemsSolicitacaoExame() {

    this.itemsSolicitacaoExame.forEach((item)=> {

      this.itemSolicitacaoExameService.adicionarItemExame(item).subscribe();
    })
  }
}
