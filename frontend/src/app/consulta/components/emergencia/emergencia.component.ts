import { Paciente } from './../../../internacao/models/paciente';
import { CRM } from './../../../internacao/models/crm';
import { Especialidade } from './../../../internacao/models/especialidade';
import { ConsultaService } from '../../consulta.service';
import { Component, OnInit, OnDestroy, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { BreadcrumbService, CALENDAR_LOCALE } from '@nuvem/primeng-components';
import { ConsultaEmergenciaModel } from '../../consulta-emergencia-model';
import { ConsultaPaciente } from '../../consulta-pacientes';

@Component({
    selector: 'app-emergencia',
    templateUrl: './emergencia.component.html',
    styleUrls: ['./emergencia.component.css'],
})
export class EmergenciaComponent implements OnInit, OnDestroy {
    consultas: any;

    @Input() private id: number;
    private paciente: ConsultaPaciente;

    @Input() public name = 'especialidade';
    @Output() public select = new EventEmitter();

    public especialidades = new Array<Especialidade>();
    public crm = new Array<CRM>();
    public pacientes = new Array<Paciente>();
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
    fds;
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
        this.aoDigitar();
        this.buscaProfissional();
    }

    cadastrarConsultas(form: FormBuilder) {
        const cadConsulta = this.formEmergencia.value;
        const consultasEmergencia: ConsultaEmergenciaModel = {
            numeroConsulta: cadConsulta.numeroConsulta,
            dataHoraDaConsulta: cadConsulta.dataHoraDaConsulta,
            grade: cadConsulta.grade,
            prontuario: cadConsulta.prontuario,
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

    aoDigitar(evento?) {
        this.consultaService
            .buscarEspecialidades()
            .subscribe((especialidades: Array<Especialidade>) => {
                this.especialidades = especialidades;
            });
        console.log(this.especialidades);
    }

    buscaProfissional(evento?) {
        this.consultaService.buscarProfissionais().subscribe((crms: Array<CRM>) => {
            this.crm = crms;
        });
        console.log(this.crm);
    }

    listarPacientes() {
        this.consultaService.buscarPaciente().subscribe((pacientes: Array<Paciente>) => {
            this.pacientes = pacientes;
        });
    }

    aoSelecionarPaciente(selectPaciente: ConsultaPaciente): void {
        this.formEmergencia.controls['prontuario'].setValue(selectPaciente.id);
    }
    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }
}