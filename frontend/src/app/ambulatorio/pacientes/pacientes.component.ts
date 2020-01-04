import { Component, OnInit } from '@angular/core';
import { LocaleUtil } from '../../util/locale.util';

@Component({
  selector: 'app-pacientes',
  templateUrl: './pacientes.component.html',
})
export class PacientesComponent implements OnInit {

  locale = LocaleUtil.pt_Br;
  
  constructor() { }

  ngOnInit() {
  }

}
