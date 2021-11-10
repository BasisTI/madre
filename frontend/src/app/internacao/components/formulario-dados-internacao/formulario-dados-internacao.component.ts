import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Internacao } from '@internacao/models/internacao';
import { FormularioTransferirPacienteService } from '@internacao/services/formulario-transferir-paciente.service';

@Component({
    selector: 'app-formulario-dados-internacao',
    templateUrl: './formulario-dados-internacao.component.html',
    styleUrls: ['./formulario-dados-internacao.component.css'],
})
export class FormularioDadosInternacaoComponent implements OnInit {

    constructor(
        private internacaoDePacienteService: FormularioTransferirPacienteService,
        private fb: FormBuilder,
    ) {}

    formularioTransferirPaciente = this.fb.group({
      id: [null],
      pacienteId: [null, [Validators.required]],
     });

    // transferir() {
    //     let alta = this.formularioTransferirPaciente.value;
    //     let acao: Internacao = {
    //         id: alta.id,
    //         pacienteId: alta.pacienteId,
    //     };

    //     this.internacaoDePacienteService.transferirPaciente(acao).subscribe();
    // }

    ngOnInit(): void{
    }
}
