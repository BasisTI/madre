import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-responsavel',
  templateUrl: './responsavel.component.html',
  styles: [],
})
export class ResponsavelComponent {
  responsavel: FormGroup = this.fb.group({
    nomeDoResponsalvel: [''],
    grauDeParentesco: [''],
    ddd: [''],
    telefone: [''],
    observacao: [''],
  });

  constructor(private fb: FormBuilder) {}
}
