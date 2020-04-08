import { Component } from '@angular/core';

import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { ptBR } from '../../../shared/calendar.pt-br.locale';

@Component({
  selector: 'app-cartao-sus',
  templateUrl: './cartao-sus.component.html',
  styles: [
    `
      .ui-widget.read-only:disabled {
        opacity: 1;
        background-color: #dddddd;
      }

      div {
        margin: 3px;
      }
    `,
  ],
})
export class CartaoSusComponent {
  cartaoSUS: FormGroup = this.fb.group({
    numero: ['', Validators.required],
    justificativa: [''],
    motivoCadastro: [''],
    docReferencia: [''],
    cartaoNacional: [''],
    dataDeEntrada: [''],
    dataDeNaturalizacao: [''],
    portaria: [''],
  });

  listaAusenciaCns = [
    { label: 'Selecione' },
    { label: 'Extravio', value: 'extravio'[''] },
    { label: 'Perda', value: 'perda'[''] },
    { label: 'Estrangeiro', value: 'estrangeiro'[''] },
    { label: 'Especiais', value: 'especiais'[''] },
    { label: 'Outros', value: 'outros'[''] },
  ];

  listaDocReferencia = [
    { label: 'Selecione' },
    { label: 'APAC', value: 'a1' },
    { label: 'AIH', value: 'a2' },
  ];

  listaMotivoCadastro = [
    { label: 'Selecione' },
    { label: 'Tratatamento Renal Substitutivo', value: 'tratRenal' },
    { label: 'Recém Nato', value: 'recemNato' },
    { label: 'Gestante', value: 'gestante' },
    { label: 'Hanseníase', value: 'hanseniase' },
    { label: 'Programa de Volta para Casa', value: 'voltaParaCasa' },
    { label: 'Estabelecimento Prisional', value: 'estabPrisional' },
    { label: 'Medicamento Excepcional', value: 'medicExcepc' },
    { label: 'Radioterapia', value: ' radio' },
    { label: 'Quimioterapia', value: 'quimio' },
    { label: 'Acompanhamento Pós-transplante', value: 'posTransplante' },
    { label: 'Contagem de Linfócitos T CD4/CD8', value: 'tCd4Cd8' },
    { label: 'Quantificação Carga Viral HIV', value: 'quantHiv' },
    { label: 'Demais proc. que exigem autorização prévia', value: 'autPrevia' },
    { label: 'Cirurgia Eletivas de Transplante', value: 'cirurgiaTranplante' },
    { label: 'Demais Cirurgias Eletivas', value: 'tuberculos' },
    { label: 'Tuberculose', value: 'tuberculose' },
    { label: 'Outros', value: 'outros' },
  ];

  localizacao = ptBR;
  maxDate = new Date();
  yearRange = `1900:${this.maxDate.getFullYear()}`;

  constructor(private fb: FormBuilder) {}
}
