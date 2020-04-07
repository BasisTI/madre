import { Component } from '@angular/core';

import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-endereco',
  templateUrl: './endereco.component.html',
})
export class EnderecoComponent {
  endereco: FormGroup = this.fb.group({
    municipio: [''],
    CEP: [''],
    UF: [''],
    logradouro: [''],
    Numero: [''],
    Complemento: [''],
    Bairro: [''],
    tipo: [''],
    corespondencia: [''],
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
