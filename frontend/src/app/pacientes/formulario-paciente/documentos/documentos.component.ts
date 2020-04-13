import { ptBR } from './../../../shared/calendar.pt-br.locale';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';

@Component({
  selector: 'app-documentos',
  templateUrl: './documentos.component.html',
  styles: [
    `
      div {
        margin: 3px;
      }
    `,
  ],
})
export class DocumentosComponent {
  documentos: FormGroup = this.fb.group(
    {
      numeroIdentidade: ['', [this.customRequired]],
      orgaoEmissor: ['', [this.customRequired]],
      uf: ['', [this.customRequired]],
      data: ['', [this.customRequired]],
      cpf: [''],
      pisPasep: [''],
      cnh: [''],
      validadeCNH: ['', this.customRequiredCNH],
    },
    { updateOn: 'blur', validators: [this.validateGroup, this.validateGroupCNH] },
  );

  listaDeOrgaos = [
    { label: 'Selecione', value: null },
    { label: 'SSP-Secretária de Segurança Pública', value: 'Secretária de Segurança Pública' },
    { label: 'Policia Federal', value: 'Policia Federal' },
    { label: 'Ministério do Exército', value: 'Ministério do Exército' },
    { label: 'Ministério da Marinha', value: 'Ministério da Marinha' },
    { label: 'Conselho Regional de Contabilidade', value: 'Conselho Regional de Contabilidade' },
    { label: 'Conselho Regional de Enfermagem', value: 'Conselho de Enfermagem' },
  ];

  listaDeUF = [
    { label: 'Selecione', value: null },
    { label: 'AC - ACRE', value: 'ACRE' },
    { label: 'AL - ALAGOAS', value: 'ALAGOAS' },
    { label: 'AM - AMAZONAS', value: 'AMAZONAS' },
    { label: 'BA - BAHIA', value: 'BAHIA' },
    { label: 'CE - CEARA', value: 'CEARA' },
    { label: 'DF - DISTRITO FEDERAL', value: 'DISTRITO FEDERAL' },
  ];

  localizacao = ptBR;
  maxDate = new Date();
  yearRange = `1900:${this.maxDate.getFullYear()}`;

  validade = ptBR;
  yearValidade = '2010:2030';

  customRequired(control: AbstractControl): { [key: string]: boolean } | null {
    if (control.parent && control.parent.get('numeroIdentidade').value && !control.value) {
      return { required: true };
    } else {
      return null;
    }
  }

  validateGroup(group: FormGroup): { [key: string]: boolean } | null {
    if (group.get('numeroIdentidade').value) {
      group.markAsDirty();
      return { required: true };
    } else {
      return null;
    }
  }

  validateGroupCNH(group: FormGroup): { [key: string]: boolean } | null {
    if (group.get('cnh').value) {
      group.markAsDirty();
      return { required: true };
    }

    return null;
  }

  customRequiredCNH(control: AbstractControl): { [key: string]: boolean } | null {
    if (control.parent && control.parent.get('cnh').value && !control.value) {
      return { required: true };
    }

    return null;
  }

  constructor(private fb: FormBuilder) {}
}
