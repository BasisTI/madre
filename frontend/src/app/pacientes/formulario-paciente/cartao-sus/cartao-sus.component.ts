import { Component } from '@angular/core';

import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-cartao-sus',
  templateUrl: './cartao-sus.component.html',
  styles: [],
})
export class CartaoSusComponent {
  cartaoSUS: FormGroup = this.fb.group({
    numero: [''],
    justificativa: [''],
    motivoCadastro: [''],
    docReferencia: [''],
    cartaoNacional: [''],
    dataDeEntrada: [''],
    dataDeNaturalizacao: [''],
    portaria: [''],
  });

  constructor(private fb: FormBuilder) {}
}
