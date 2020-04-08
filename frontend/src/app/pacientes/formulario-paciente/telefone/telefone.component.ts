import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component } from '@angular/core';

@Component({
  selector: 'app-telefone',
  templateUrl: './telefone.component.html',
  styles: [
    `
      div {
        margin: 3px;
      }
    `,
  ],
})
export class TelefoneComponent {
  constructor(private fb: FormBuilder) {}
  opcao = [
    { label: 'selecione' },
    { label: 'celular' },
    { label: 'residencial' },
    { label: 'comercial' },
    { label: 'emergencial' },
  ];

  telefone: FormGroup = this.fb.group({
    tipo: [''],
    telefone: ['', Validators.required],
    observacao: [''],
    DDD: ['', Validators.required],
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
