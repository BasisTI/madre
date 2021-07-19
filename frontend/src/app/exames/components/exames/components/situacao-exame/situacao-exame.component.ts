import { Component, OnInit } from '@angular/core';
import { SituationDropdown } from 'src/app/exames/models/dropdowns/situation.dropdown';

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

  constructor() { }

  ngOnInit(): void {
  }

}
