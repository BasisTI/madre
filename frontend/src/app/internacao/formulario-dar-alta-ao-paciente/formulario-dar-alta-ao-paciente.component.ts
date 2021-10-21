import { TipoDeAltaDropdown } from './../models/dropdowns/tipo-de-alta.dropdown';
import { DarAltaAoPaciente } from '@internacao/models/dar-alta-ao-paciente';
import { FormularioDarAltaAoPacienteService } from './../services/formulario-dar-alta-ao-paciente.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-formulario-dar-alta-ao-paciente',
  templateUrl: './formulario-dar-alta-ao-paciente.component.html',
  styleUrls: ['./formulario-dar-alta-ao-paciente.component.css']
})
export class FormularioDarAltaAoPacienteComponent implements OnInit {

  constructor( private fb: FormBuilder,
                private formualrioDarAltaService: FormularioDarAltaAoPacienteService ) {}

    tiposdeAltas = TipoDeAltaDropdown;

    altaForm = this.fb.group({
        tipoDeAlta: [null, Validators.required],
    })

    cadastra() {
        let cadastro = this.altaForm.value

        let darAlta: DarAltaAoPaciente = {
            tipoDeAlta: cadastro.tipoDeAlta
        }
    }





    ngOnInit(): void {}

}
