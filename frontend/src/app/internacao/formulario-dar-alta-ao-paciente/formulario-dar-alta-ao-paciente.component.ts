import { FormularioDarAltaAoPacienteService } from './../services/formulario-dar-alta-ao-paciente.service';
import { DarAltaAoPaciente } from './../models/dar-alta-ao-paciente';
import { FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-formulario-dar-alta-ao-paciente',
  templateUrl: './formulario-dar-alta-ao-paciente.component.html',
  styleUrls: ['./formulario-dar-alta-ao-paciente.component.css']
})
export class DarAltaAoPacienteComponent implements OnInit {

    nome: string;
    prontuario: number;

  constructor(private fb: FormBuilder,
              private darALtaPaciente: FormularioDarAltaAoPacienteService,
  ) {}

  formularioDarAltaPaciente = this.fb.group({
    nome: [null, Validators.required],
    prontuario: [null, Validators.required]
  });

  ngOnInit(): void {
  }

  formulario(){
      let darAltaPaciente = this.formularioDarAltaPaciente.value;

      let cadastro: DarAltaAoPaciente = {
          nome: darAltaPaciente.nome,
          prontuario: darAltaPaciente.protuario,
      };


      console.log(darAltaPaciente);

      this.darALtaPaciente.formularioDarAltaPaciente(cadastro).subscribe();

  }

  validaFormulario() {
      if(this.formularioDarAltaPaciente.valid) return true;
  }

  limpaFormulario() {
      this.formularioDarAltaPaciente.reset();
  }


}
