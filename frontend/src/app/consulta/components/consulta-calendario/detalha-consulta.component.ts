import { PacienteModel } from './../../models/paciente-model';
import { ConsultaService } from './../../consulta.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { BreadcrumbService, CALENDAR_LOCALE } from '@nuvem/primeng-components';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ConsultaEmergenciaModel } from '../../models/consulta-emergencia-model';


@Component({
    selector: 'app-detalha-consulta',
    templateUrl: './detalha-consulta.component.html',
})
export class DetalhaConsultaComponent implements OnInit, OnDestroy {
    detalharConsultas = this.fb.group({
        pacienteId: [''],
        nome: [''],
        prontuario: [''],
        dataHoraDaConsulta: [''],
        grade: [''],
        numeroSala: [''],
        turno: [''],
        tipoPagador: [''],
        gradesDisponiveis: [''],
        clinicaCentralId: [''],
        justificativa: [''],
        observacoes: [''],
        condicaoDeAtendimentoId: [''],
        formaDeAgendamentoId: [''],
        numeroConsulta: [''],
        especialidade: [''],
        profissional: [''],
        gradesDiponiveis: [''],
    });

    localizacao = CALENDAR_LOCALE;
    dataLimite = new Date();
    anosDisponiveis = `2010:${this.dataLimite.getFullYear()}`;
    formatoDeData = 'dd/mm/yy';
    public consultasEmergenciais = Array<ConsultaEmergenciaModel>();
    public pacientes = new Array<PacienteModel>();

    constructor(
        private fb: FormBuilder,
        private breadcrumbService: BreadcrumbService,
        private route: ActivatedRoute,
        private consultaService: ConsultaService,
    ) {}

    ngOnInit(): void {
        this.breadcrumbService.setItems([
            {
                label: 'Consultas',
                routerLink: 'consulta',
            },
            {
                label: 'Calendário',
                routerLink: 'consulta-calendario',
            },
            {
                label: 'Detalhamento de Consultas',
                routerLink: 'detalha-consulta',
            },
        ]);

        const consultaId = this.route.snapshot.params['id'];

        if (consultaId) {
            this.carregarConsulta(consultaId);
        }
    }

    carregarConsulta(id: number) {
        this.consultaService
            .buscarConsultaId(id)
            .subscribe((consultasEmergencia: ConsultaEmergenciaModel) => {
                this.detalharConsultas.patchValue(consultasEmergencia);
                this.carregarPaciente();
            });
    }

    carregarPaciente(){
      this.consultaService.buscarPacientesPorId(this.detalharConsultas.value.pacienteId).subscribe((paciente: PacienteModel) =>{
        this.detalharConsultas.controls['nome'].setValue(paciente.nome);
        this.detalharConsultas.controls['prontuario'].setValue(paciente.prontuario);
      });

    }
    
    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }
}
