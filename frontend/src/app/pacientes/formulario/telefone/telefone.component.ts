import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { DatatableComponent } from '@nuvem/primeng-components';
import { OPCOES_DE_TIPO_DE_TELEFONE } from '../../models/dropdowns/opcoes-de-tipo-de-telefone';
import { DDD } from '../../models/dropdowns/types/DDD';
import { Telefone } from '../paciente.model';
import { DDDService } from './ddd.service';
import { PacienteValidators } from "./../paciente.validators";

@Component({
  selector: 'app-telefone',
  templateUrl: './telefone.component.html',
  styleUrls: ['./telefone.component.css']
})
export class TelefoneComponent implements OnInit {

  @Input()
  public formGroup: FormGroup;

  @ViewChild(DatatableComponent)
  datatable: DatatableComponent;

  listaDDD = new Array<DDD>();

  validaTelefone: boolean;

  tiposDeTelefone = OPCOES_DE_TIPO_DE_TELEFONE;

  tipoDeTelefoneSelecionado: string;

  mascara: string = '9999-9999';

  telefone = this.fb.group({
    id: [''],
    ddd: ['', Validators.required],
    numero: ['', Validators.required],
    tipo: [''],
    observacao: [''],
    indice: [''],
  });


  constructor(private DDDService: DDDService, private fb: FormBuilder) { }

  ngOnInit(): void {
  }

  buscaDDD(event) {
    this.DDDService.getResultDDD(event.query).subscribe((data) => {
      this.listaDDD = data;
    })
  }

  adicionarTelefoneALista() {
    const form = this.telefone.value;
    const validate = PacienteValidators.validateTelefone(form);
    if(!validate.required){
        this.telefone.patchValue({ indice: this.formGroup.value.telefones.length });
        const telefone: Telefone = {
          id: form.id,
          ddd: form.ddd.valor,
          numero: form.numero,
          tipo: form.tipo,
          observacao: form.observacao,
        };

        this.formGroup.value.telefones.unshift(telefone);
        this.telefone.reset();
    }
  }

  tipoDeMascara(event) {
    this.tipoDeTelefoneSelecionado = event.value;
    if (this.tipoDeTelefoneSelecionado === 'CELULAR') {
      this.mascara = '9 9999-9999';
    }else{
        this.mascara = '9999-9999'
    }
  }

}
