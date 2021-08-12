import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { OPCOES_DE_SITUACOES } from '@internacao/formulario-unidades/models/dropwdowns/types/opcoes-de-situacoes';
import { Cargos } from '../../models/dropdowns/cargos';
import { OcupacoesDeCargoModel } from '../../models/ocupacoes-de-cargo-model';
import { CargosService } from '../../services/cargos.service';
import { OcupacoesDeCargoService } from '../../services/ocupacoes-de-cargos.service';

@Component({
  selector: 'app-ocupacoes-de-cargo',
  templateUrl: './ocupacoes-de-cargo.component.html',
  styleUrls: ['./ocupacoes-de-cargo.component.css']
})
export class OcupacoesDeCargoComponent implements OnInit {

  formOcupacoesDeCargo = this.fb.group ({
    id: [''],
    codigo: ['', Validators.required],
    descricao: ['', Validators.required],
    situacao: ['', Validators.required],
    informarCbo: [''],
    informarCns: [''],
  })

  cargos: Cargos[] = [];

  cargoTeste: number;

  situacaoDoServidor = OPCOES_DE_SITUACOES;

  constructor( 
    private cargosService: CargosService,
    private fb: FormBuilder, 
    private ocupacoesDeCargoService: OcupacoesDeCargoService,
  ) { }
  
  ngOnInit(): void {
    this.cargosService.getCargos().subscribe((response) => {this.cargos = response; this.cargos.unshift({ id: null, descricao: "Selecione" }) })
  }
  
  valid(): boolean {
    return this.formOcupacoesDeCargo.valid;
  }

  submit() {
    const occ = this.formOcupacoesDeCargo.value;
    const ocupacoesDeCargo: OcupacoesDeCargoModel = {
      id: occ.id,
      codigo: occ.codigo,
      descricao: occ.descricao,
      situacao: occ.situacao,
      informarCbo: occ.informarCbo,
      informarCns: occ.informarCns,
      cargoId: this.cargoTeste,
    };
    console.log(ocupacoesDeCargo)

    if (this.formOcupacoesDeCargo.value.id != 0) {
      this.ocupacoesDeCargoService.alterarOcupacoesDeCargo(ocupacoesDeCargo).subscribe((e) => {
        this.formOcupacoesDeCargo.reset();
      });
    } else {
      this.ocupacoesDeCargoService.cadastrarOcupacoesDeCargo(ocupacoesDeCargo).subscribe((e) => {
        this.formOcupacoesDeCargo.reset();
      });
    } 
  }

}
