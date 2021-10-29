import { DarAltaAoPacienteService } from './../services/dar-alta-ao-paciente.service';
import { SituacaoAtivoInternacao } from './../models/dropdowns/situacao.dropdown';
import { TipoDeAltaDropdown } from './../models/dropdowns/tipo-de-alta.dropdown';
import { DarAltaAoPaciente } from '@internacao/models/dar-alta-ao-paciente';
import { FormBuilder, Validators } from '@angular/forms';
import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
    selector: 'app-formulario-dar-alta-ao-paciente',
    templateUrl: './formulario-dar-alta-ao-paciente.component.html',
    styleUrls: ['./formulario-dar-alta-ao-paciente.component.css'],
})
export class FormularioDarAltaAoPacienteComponent implements OnInit {
    dataInternacao: Date;
    dataAlta: Date;
    previsaoDeAlta: Date;

    protuarioPaciente: string;
    nomePaciente: string;
    tipoDeAlta: string;
    /* situacao: SituacaoAtivoInternacao; */

    tiposDeAltas = TipoDeAltaDropdown;

    @Input()
    alta: DarAltaAoPaciente;

    @Output()
    salvaAlta = new EventEmitter<DarAltaAoPaciente>();

    constructor(private fb: FormBuilder, private darAltaService: DarAltaAoPacienteService) {}

    cadastroAlta = this.fb.group({
        dataInternacao: [null, Validators.required],
        dataAlta: [null, Validators.required],
        previsaoDeAlta: [null, Validators.required],
        tipoDeAlta: [null, Validators.required],
    });

    cadastraDarAlta() {
        const cadastro = this.cadastroAlta.value;

        this.alta = {
            dataDaInternacao: this.dataInternacao,
            dataDaAlta: this.dataAlta,
            previsaoDeAlta: this.previsaoDeAlta,
            tipoDeAlta: this.tipoDeAlta,
        };

        this.darAltaService.cadastrarAlta(this.alta).subscribe((response) => {
            Object.assign(this.alta, response);
            this.salvaAlta.emit(this.alta);
        });
    }

    ngOnInit(): void {}
}
