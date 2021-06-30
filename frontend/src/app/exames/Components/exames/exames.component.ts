import { Component, OnInit } from '@angular/core';
import { SituationDropdown } from "../../models/dropdowns/situation.dropdown";

@Component({
  selector: 'app-exames',
  templateUrl: './exames.component.html',
  styleUrls: ['./exames.component.scss']
})
export class ExamesComponent implements OnInit {

  // Por exame
  selectedValue: String;
  unitCheckbox: Boolean;
  text: String;
  urgentCheck: Boolean;
  date: Date;
  selectedSituation: String;

  // Por lote
  selectedLote: String;
  group: String;
  results: String[];
  teste: String[] = ["Ana", "Rogerio", "Gustavo"];
  situationDropdown = SituationDropdown;


  constructor() { }

  handleDropdown(event) {
    //event.query = current value in input field
  }


  ngOnInit(): void {
  }

}
