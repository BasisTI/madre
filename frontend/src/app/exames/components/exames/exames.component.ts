import { Component, OnInit, OnDestroy } from '@angular/core';
import { GroupDropdown } from '../../models/dropdowns/groups.dropdown';
import { SituationDropdown } from "../../models/dropdowns/situation.dropdown";
import { BreadcrumbService, CALENDAR_LOCALE, DatatableComponent } from '@nuvem/primeng-components';
import { ExamModel } from '../../models/subjects/exames-model';
import { GruposExamesService } from '../../services/grupos-exames.service';
import { GrupoModel } from '../../models/subjects/grupo-model';
// import { AutoCompleteDemo } from '../../models/dropdowns/exams.dropdown';

@Component({
  selector: 'app-exames',
  templateUrl: './exames.component.html',
  styleUrls: ['./exames.component.scss']
})
export class ExamesComponent implements OnInit, OnDestroy {

  fakeExame: ExamModel[] = [
    {
      id: 1,
      nome: "Ambulatório",
      nomeusual: "string",
      sigla: "string",
      materialExameId: 3921839,
      material: "string",
      amostraExameId: 98328911,
      amostraExameNome: "string",
    },
    {
      id: 2,
      nome: "maca",
      nomeusual: "string",
      sigla: "MC",
      materialExameId: 8234783,
      material: "tecido",
      amostraExameId: 58239,
      amostraExameNome: "nada",
    }
  ]

  // Por exame:
  situationDropdown = SituationDropdown;
  selectedValue: String;
  unitCheckbox: Boolean;
  selectedExam: ExamModel;
  urgentCheck: Boolean;
  date: Date;
  selectedSituation: String;
  public exames = new Array<ExamModel>();
  // options = AutoCompleteDemo;

  // Por lote:
  results: String[];
  selectedLote: String;
  group: String = null;
  groups: GrupoModel[];


  constructor(
    private breadcrumbService: BreadcrumbService,
    private gruposExamesService: GruposExamesService,
    ) { }

  handleClick() {
    console.log("For exam Data");
    console.log(this.selectedValue);
    console.log(this.unitCheckbox);
    console.log(this.selectedExam);
    console.log(this.urgentCheck);
    console.log(this.date);
    console.log(this.selectedSituation);

    
    console.log("For Lote Data");
    console.log(this.selectedLote);
    console.log(this.group);
  }

  listarExames(){ }

  aoSelecionarExame(){ }


  ngOnInit(): void{
    this.breadcrumbService.setItems([
      {
        label: 'Exames',
      },
      { 
        label: 'Solicitar Exame',
      }
    ])
    this.gruposExamesService.GetGrupos().subscribe((response) => {
      this.groups = response;
    })

  }

  // Métodos

  Listar() {
    
  }
  
  ngOnDestroy(): void {
    this.breadcrumbService.reset();
  }
}
