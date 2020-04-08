import { Component } from '@angular/core';

import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-endereco',
  templateUrl: './endereco.component.html',
  styles: [
    `
      div {
        margin: 3px;
      }
    `,
  ],
})
export class EnderecoComponent {
  opcao = [
    { label: 'selecione' },
    { label: 'residencial' },
    { label: 'comercial' },
    { label: 'outros' },
  ];

  enderecos: Array<any>;
  endereco: FormGroup = this.fb.group({
    municipio: ['', Validators.required],
    CEP: ['', Validators.required],
    UF: ['', Validators.required],
    logradouro: ['', Validators.required],
    Numero: ['', Validators.required],
    Complemento: [''],
    Bairro: ['', Validators.required],
    tipo: ['', Validators.required],
    corespondencia: ['', Validators.required],
  });

  fakeData = [
    {
      municipio: 'São paulo',
      CEP: '71717771771',
      UF: 'SP',
      logradouro: 'casa 12',
      Numero: '22',
      Complemento: 'teste',
      Bairro: 'flamengo',
      tipo: 'recidencia',
      corespondencia: 'sim',
    },
    {
      municipio: 'Acre ',
      CEP: '727272772',
      UF: 'AC',
      logradouro: 'casa 33',
      Numero: '33',
      Complemento: 'teste2',
      Bairro: 'Rio Branco',
      tipo: 'comercial',
      corespondencia: 'nao',
    },
  ];

  constructor(private fb: FormBuilder) {}
}
