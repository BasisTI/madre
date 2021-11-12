import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { DatatableComponent } from '@nuvem/primeng-components';
import { OPCOES_DE_TIPO_DE_TELEFONE } from '../../models/dropdowns/opcoes-de-tipo-de-telefone';
import { DDD } from '../../models/dropdowns/types/DDD';
import { Telefone } from '../paciente.model';
import { DDDService } from './ddd.service';

@Component({
  selector: 'app-telefone',
  templateUrl: './telefone.component.html',
  styleUrls: ['./telefone.component.css']
})
export class TelefoneComponent implements OnInit {

  @Input()
  telefones: any = [];

  @ViewChild(DatatableComponent)
  datatable: DatatableComponent;

  listaDDD = new Array<DDD>();

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


  constructor(private dddService: DDDService, private fb: FormBuilder) { }

  ngOnInit(): void {
  }

  buscaDDD(event) {
    this.dddService.getResultDDD(event.query).subscribe((data) => {
      this.listaDDD = data;
    })
  }

  adicionarTelefoneALista() {
    const form = this.telefone.value;
    this.telefone.patchValue({ indice: this.telefones.length });
    const telefone: Telefone = {
      id: form.id,
      ddd: form.ddd.valor,
      numero: form.numero,
      tipo: form.tipo,
      observacao: form.observacao,
    };
    this.telefones.push(telefone);
    this.telefone.reset();
    console.log(this.telefone.value)
    console.log(telefone)
  }

  tipoDeMascara(event) {
    this.tipoDeTelefoneSelecionado = event.value;
    if (this.tipoDeTelefoneSelecionado === 'CELULAR') {
      this.mascara = '9 9999-9999';
    }
  }

}
