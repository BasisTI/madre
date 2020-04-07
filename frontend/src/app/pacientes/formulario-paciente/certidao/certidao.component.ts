import { Component } from '@angular/core';

import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-certidao',
  templateUrl: './certidao.component.html',
  styles: [],
})
export class CertidaoComponent {
  certidao: FormGroup = this.fb.group({
    registroDeNascimento: [''],
    tipoCertidao: [''],
    nomeDoCartorio: [''],
    livro: [''],
    folhas: [''],
    termo: [''],
    dataDeEmissao: [''],
    numeroDaDN: [''],
  });

  constructor(private fb: FormBuilder) {}
}
