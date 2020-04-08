import { FormGroup, FormBuilder } from '@angular/forms';
import { Component } from '@angular/core';

@Component({
  selector: 'app-telefone',
  templateUrl: './telefone.component.html',
})
export class TelefoneComponent {
  constructor(private fb: FormBuilder) {}
  cities1 = [
    { label: 'selecione' },
    { label: 'celular' },
    { label: 'residencial' },
    { label: 'comercial' },
    { label: 'emergencial' },
  ];

  telefone: FormGroup = this.fb.group({
    tipo: [''],
    telefone: [''],
    observacao: [''],
    DDD: [''],
  });

  fakeData = [
    {
      tipoDeTelefone: 'residencial',
      telefone: '999999999',
      observacao: 'teste',
    },
    { tipoDeTelefone: 'comercial', telefone: '9888888888', observacao: 'teste2' },
  ];
}
