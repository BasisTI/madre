import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { OPCOES_DE_SITUACOES } from '@internacao/formulario-unidades/models/dropwdowns/types/opcoes-de-situacoes';
import { CargoModel } from '../../models/cargo-model';
import { CargosService } from '../../services/cargos.service';

@Component({
  selector: 'app-cargos',
  templateUrl: './cargos.component.html',
  styleUrls: ['./cargos.component.css']
})
export class CargosComponent implements OnInit {

  formCargo = this.fb.group ({
    id: [''],
    codigo: ['', Validators.required],
    descricao: ['', Validators.required],
    situacao: ['', Validators.required],
  })

  situacaoDoServidor = OPCOES_DE_SITUACOES;


  constructor(
    private fb: FormBuilder,
    private cargoService: CargosService,
  ) { }

  ngOnInit(): void {
  }

  valid(): boolean {
    return this.formCargo.valid;
  }

  submit() {
    const occ = this.formCargo.value;
    const cargo: CargoModel = {
      id: occ.id,
      codigo: occ.codigo,
      descricao: occ.descricao,
      situacao: occ.situacao,
    };

    if (this.formCargo.value.id != 0) {
      this.cargoService.alterarCargo(cargo).subscribe((e) => {
        this.formCargo.reset();
      });
    } else {
      this.cargoService.cadastrarCargo(cargo).subscribe((e) => {
        this.formCargo.reset();
      });
    } 
  }

}
