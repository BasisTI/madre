import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-documentos',
  templateUrl: './documentos.component.html',
})
export class DocumentosComponent {
  documentos: FormGroup = this.fb.group({
    numeroIdentidade: [''],
    nomeSocial: [''],
    orgaoEmissor: [''],
    uf: [''],
    data: [''],
    cpf: [''],
    pisPasep: [''],
    cnh: [''],
    validadeCNH: [''],
  });

  constructor(private fb: FormBuilder) {}
}
