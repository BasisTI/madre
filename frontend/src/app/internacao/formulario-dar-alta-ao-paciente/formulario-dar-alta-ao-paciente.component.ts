import { CALENDAR_LOCALE } from '@nuvem/primeng-components';
import { ConfiguracaoParaCalendarioPrimeNG } from './../../shared/p-calendar.config';
import { PrioridadeDropdown } from '@internacao/models/dropdowns/prioridades.dropdown';
import { DarAltaAoPacienteService } from './../services/dar-alta-ao-paciente.service';
import { TipoDeAltaDropdown } from './../models/dropdowns/tipo-de-alta.dropdown';
import { DarAltaAoPaciente } from '@internacao/models/dar-alta-ao-paciente';
import { FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-formulario-dar-alta-ao-paciente',
    templateUrl: './formulario-dar-alta-ao-paciente.component.html',
    styleUrls: ['./formulario-dar-alta-ao-paciente.component.css'],
})
export class FormularioDarAltaAoPacienteComponent implements OnInit {
    id: string;
    pacienteProntuario: string;
    pacienteId: string;
    pacienteNome: string;
    leitoId: string;
    tipoDeAlta: string;
    prioridade: string;
    justificativa: string;
    ativo: boolean;
    convenioDeSaude: string;
    especialidade: string;

    dataInternacao: Date;
    dataAlta: Date;
    previsaoDeAlta: Date;

    tiposDeAltas = TipoDeAltaDropdown;
    prioridades = PrioridadeDropdown;

    formularioAltaPaciente = this.fb.group({
        id: [null],
        pacienteProntuario: [null, Validators.required],
        pacienteId: [null, Validators.required],
        pacienteNome: [null, Validators.required],
        leitoId: [null, Validators.required],
        dataInternacao: [null, Validators.required],
        dataAlta: [null, Validators.required],
        previsaoDeAlta: [null, Validators.required],
        tipoDeAlta: [null, Validators.required],
        ativo: [null, Validators.required],
        prioridade: [null, Validators.required],
        justificativa: [null, Validators.required],
        convenioDeSaude: [null],
        especialidade: [null],
    });

    public pCalendarConfig: ConfiguracaoParaCalendarioPrimeNG = {
        anosDisponiveis: '1900:2100',
        formatoDeData: 'dd/mm/yyyy',
        localidade: CALENDAR_LOCALE,
    };

    constructor(private fb: FormBuilder, private darAltaService: DarAltaAoPacienteService) {}

    ngOnInit(): void {}

    validaFormularioAlta() {
        if (this.formularioAltaPaciente.valid) return true;
    }

    formularioAlta() {
        let alta = this.formularioAltaPaciente.value;

        let acao: DarAltaAoPaciente = {
            id: alta.id,
            pacienteProntuario: alta.pacienteProntuario,
            pacienteId: alta.pacienteId,
            pacienteNome: alta.pacienteNome,
            leitoId: alta.leitoId,
            dataDaInternacao: alta.dataInternacao,
            dataDaAlta: alta.dataAlta,
            previsaoDeAlta: alta.previsaoDeAlta,
            tipoDeAlta: alta.tipoDeAlta,
            ativo: alta.ativo,
            prioridade: alta.prioridade,
            justificativa: alta.justificativa,
            convenioId: alta.convenioDeSaude,
            especialidadeId: alta.especialidade,
        };

        this.darAltaService.darAlta(acao).subscribe();
    }
}
