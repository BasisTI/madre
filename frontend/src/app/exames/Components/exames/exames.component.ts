import { Component, OnInit, OnDestroy } from '@angular/core';
import { GroupDropdown } from '../../models/dropdowns/groups.dropdown';
import { SituationDropdown } from "../../models/dropdowns/situation.dropdown";
import { BreadcrumbService, CALENDAR_LOCALE, DatatableComponent } from '@nuvem/primeng-components';
import { ExamModel } from '../../models/subjects/exames-model';
import { ConsultaExame } from '../../models/subjects/consulta-exame';
import { ExameService } from '../../Services/exame.service';
// import { AutoCompleteDemo } from '../../models/dropdowns/exams.dropdown';

@Component({
  selector: 'app-exames',
  templateUrl: './exames.component.html',
  styleUrls: ['./exames.component.scss']
})
export class ExamesComponent implements OnInit, OnDestroy {

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
  groupDropdown = GroupDropdown;
  results: String[];
  selectedLote: String;
  group: String;


  constructor(
    private breadcrumbService: BreadcrumbService,
    private exameService: ExameService,
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


  ngOnInit(): void{
    this.breadcrumbService.setItems([
      {
        label: 'Exames',
      },
      { 
        label: 'Solicitar Exame',
        routerLink: 'solicitar-exame',
      }
    ])
  }

  // Métodos

  listarExames() {
    this.exameService.buscarPaciente().subscribe((exames: Array<ExamModel>) => {
        this.exames = exames;
    });
  }

  // aoSelecionarExame(selectPaciente: ConsultaExame): void {
  //   this.formEmergencia.controls['prontuario'].setValue(selectPaciente.id);
  // }
  
  ngOnDestroy(): void {
    this.breadcrumbService.reset();
  }
}
