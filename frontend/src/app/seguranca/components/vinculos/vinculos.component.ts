import { Component, OnInit } from '@angular/core';
import { OPCOES_DE_SITUACOES } from '@internacao/formulario-unidades/models/dropwdowns/types/opcoes-de-situacoes';

@Component({
  selector: 'app-vinculos',
  templateUrl: './vinculos.component.html',
  styleUrls: ['./vinculos.component.css']
})
export class VinculosComponent implements OnInit {

  constructor() { }

  situacaoDoServidor = OPCOES_DE_SITUACOES;

  ngOnInit(): void {
  }

}
