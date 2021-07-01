import { Component, OnInit } from '@angular/core';
import { GroupDropdown } from '../../models/dropdowns/groups.dropdown';
import { SituationDropdown } from "../../models/dropdowns/situation.dropdown";
import { BreadcrumbService, CALENDAR_LOCALE, DatatableComponent } from '@nuvem/primeng-components';
// import { AutoCompleteDemo } from '../../models/dropdowns/exams.dropdown';

@Component({
  selector: 'app-exames',
  templateUrl: './exames.component.html',
  styleUrls: ['./exames.component.scss']
})
export class ExamesComponent implements OnInit {

  // Por exame:
  situationDropdown = SituationDropdown;
  selectedValue: String;
  unitCheckbox: Boolean;
  text: String;
  urgentCheck: Boolean;
  date: Date;
  selectedSituation: String;
  // options = AutoCompleteDemo;

  // Por lote:
  groupDropdown = GroupDropdown;
  results: String[];
  selectedLote: String;
  group: String;


  constructor(private breadcrumbService: BreadcrumbService) { }

  handleDropdown(event) {
    //event.query = current value in input field
  }

  handleClick() {
    console.log("For exam Data");
    console.log(this.selectedValue);
    console.log(this.unitCheckbox);
    console.log(this.text);
    console.log(this.urgentCheck);
    console.log(this.date);
    console.log(this.selectedSituation);

    
    console.log("For Lote Data");
    console.log(this.selectedLote);
    console.log(this.group);
  }


  ngOnInit(): void{

  }

}
