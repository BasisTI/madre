import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-responsavel',
  templateUrl: './responsavel.component.html',
  styles: [],
})
export class ResponsavelComponent {
  responsavel: FormGroup = this.fb.group({
    nomeDoResponsavel: [''],
    grauDeParentesco: [''],
    ddd: [''],
    telefone: [''],
    observacao: [''],
  });

  grausDeParentesco = [
    { label: 'Selecione', value: null },
    { label: 'Cônjuge', value: 'conjuge' },
    { label: 'Neto', value: 'neto' },
    { label: 'Neta', value: 'neta' },
    { label: 'Tio', value: 'tio' },
    { label: 'Tia', value: 'tia' },
    { label: 'Sobrinho', value: 'sobrinho' },
    { label: 'Sobrinha', value: 'sobrinha' },
    { label: 'Pai', value: 'pai' },
    { label: 'Mãe', value: 'mae' },
    { label: 'Irmão', value: 'irmao' },
    { label: 'Irmã', value: 'irma' },
    { label: 'Primo', value: 'primo' },
    { label: 'Prima', value: 'prima' },
    { label: 'Cliente', value: 'cliente' },
    { label: 'Filho', value: 'filho' },
    { label: 'Filha', value: 'filha' },
    { label: 'Amigo', value: 'amigo' },
    { label: 'Amiga', value: 'amiga' },
    { label: 'Responsável Legal', value: 'responsavelLegal' },
    { label: 'Desconhecido', value: 'desconhecido' },
    { label: 'Não Informado', value: 'naoInformado' },
  ];

  constructor(private fb: FormBuilder) {}
}
