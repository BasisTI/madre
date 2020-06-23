import { ConsultaService } from '../../consulta.service';
import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { BreadcrumbService, CALENDAR_LOCALE } from '@nuvem/primeng-components';
import { ConsultaEmergenciaModel } from '../../consulta-emergencia-model';

@Component({
    selector: 'app-emergencia',
    templateUrl: './emergencia.component.html',
    styleUrls: ['./emergencia.component.css'],
})
export class EmergenciaComponent implements OnInit, OnDestroy {
    @Input() formularioTriagem: FormGroup;
    consultasEmergencia: ConsultaEmergenciaModel;
    formEmergencia = this.fb.group({
        numeroConsulta: [''],
        dataHoraDaConsulta: [''],
        grade: [''],
        prontuario: [''],
        nome: [''],
        especialidade: [''],
        profissional: [''],
        convenio: [''],
        clinicaCentralId: [''],
        observacao: [''],
        justificativa: [''],
    });

    localizacao = CALENDAR_LOCALE;
    dataLimite = new Date();
    anosDisponiveis = `2010:${this.dataLimite.getFullYear()}`;
    formatoDeData = 'dd/mm/yy';
    constructor(
        private fb: FormBuilder,
        private consultaService: ConsultaService,
        private breadcrumbService: BreadcrumbService,
    ) {}

    ngOnInit(): void {
        this.breadcrumbService.setItems([
            {
                label: 'Consultas',
                routerLink: 'consulta',
            },
            {
                label: 'Emergência',
                routerLink: 'emergencia',
            },
        ]);
    }
    cadastrarConsultas(form: FormBuilder) {
        const cadConsulta = this.formEmergencia.value;
        const consultasEmergencia: ConsultaEmergenciaModel = {
            numeroConsulta: cadConsulta.numeroConsulta,
            dataHoraDaConsulta: cadConsulta.dataHoraDaConsulta,
            grade: cadConsulta.grade,
            nome: cadConsulta.nome,
            especialidade: cadConsulta.especialidade,
            profissional: cadConsulta.profissional,
            clinicaCentralId: cadConsulta.clinicaCentralId,
            observacao: cadConsulta.observacao,
            justificativa: cadConsulta.justificativa,
            condicaoDeAtendimentoId: cadConsulta.condicaoDeAtendimentoId,
            formaDeAgendamentoId: cadConsulta.formaDeAgendamentoId,
            pacienteId: cadConsulta.pacienteId,
        };

        this.consultaService.cadastrarConsultas(consultasEmergencia).subscribe((e) => {
            this.formEmergencia.reset();
        });
        console.log(consultasEmergencia);
    }
    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }
}
