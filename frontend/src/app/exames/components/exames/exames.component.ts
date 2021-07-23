import { Component, OnInit, OnDestroy } from '@angular/core';
import { SituationDropdown } from "../../models/dropdowns/situation.dropdown";
import { BreadcrumbService, CALENDAR_LOCALE, DatatableComponent } from '@nuvem/primeng-components';
import { ExamModel } from '../../models/subjects/exames-model';
import { GruposExamesService } from '../../services/grupos-exames.service';
import { ExamesService } from '../../services/exames.service';
import { GrupoModel } from '../../models/subjects/grupo-model';
// import { AutoCompleteDemo } from '../../models/dropdowns/exams.dropdown';

@Component({
  selector: 'app-exames',
  templateUrl: './exames.component.html',
  styleUrls: ['./exames.component.scss']
})
export class ExamesComponent implements OnInit, OnDestroy {

  exames: ExamModel[] = [];

  // Por exame:
  situationDropdown = SituationDropdown;
  selectedValue: String;
  unitCheckbox: Boolean;
  selectedExam: ExamModel;
  exameNome: string;
  urgentCheck: Boolean;
  date: Date;
  selectedSituation: String;
  // public exames = new Array<ExamModel>();
  // options = AutoCompleteDemo;

  // Por lote:
  selectedLote: String;
  group: String = null;
  groups: GrupoModel[];


  constructor(
    private breadcrumbService: BreadcrumbService,
    private gruposExamesService: GruposExamesService,
    private examesService: ExamesService
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
  
  ngOnDestroy(): void {
    this.breadcrumbService.reset();
  }
}
