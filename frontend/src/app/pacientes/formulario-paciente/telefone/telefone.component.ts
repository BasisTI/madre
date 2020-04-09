import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
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
    DDD: new FormControl('', Validators.required),
  });

  fakeData = [
    {
      tipoDeTelefone: 'residencial',
      telefone: '9 999-999',
      observacao: 'teste',
    },
    { tipoDeTelefone: 'comercial', telefone: '9 8889-8888', observacao: 'teste2' },
  ];
  adicionar(telefone) {
    this.telefone = telefone;
  }
}
