import { Component, OnInit } from '@angular/core';
import { OPCOES_DE_ANTIMICROBIANOS } from '../../models/dropdowns/opcoes-de-antimicrobianos';
import { OPCOES_DE_EXAME_OU_COMPARATIVO } from '../../models/dropdowns/opcoes-de-exame-ou-comparativo';

@Component({
  selector: 'app-solicitar-exame',
  templateUrl: './solicitar-exame.component.html',
  styleUrls: ['./solicitar-exame.component.css']
})
export class SolicitarExameComponent implements OnInit {

  constructor() { }

  opcoesDeAntimicrobianos = OPCOES_DE_ANTIMICROBIANOS;
  opcoesDeExameOuComparativo = OPCOES_DE_EXAME_OU_COMPARATIVO;

  ngOnInit(): void {
  }

}
