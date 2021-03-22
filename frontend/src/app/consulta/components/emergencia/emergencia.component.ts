import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BreadcrumbService, CALENDAR_LOCALE } from '@nuvem/primeng-components';
import { ConsultaService } from '../../consulta.service';
import { ConsultaEmergenciaModel } from '../../models/consulta-emergencia-model';
import { ConsultaPaciente } from '../../models/consulta-pacientes';
import { PacienteModel } from '../../models/paciente-model';
import { CRM } from './../../../internacao/models/crm';
import { Especialidade } from './../../../internacao/models/especialidade';
import { OPCAO_TIPO_PAGADOR_CONSULTA } from './../../consulta-opcoes/opcao-tipo-pagador-consulta';
import { OPCOES_TURNO_CONSULTA } from './../../consulta-opcoes/opcao-turno-consulta';

@Component({
    selector: 'app-emergencia',
    templateUrl: './emergencia.component.html',
})
export class EmergenciaComponent implements OnInit, OnDestroy {
    consultas: any;

    @Input() private id: number;
    private paciente: ConsultaPaciente;

    @Input() public name = 'especialidade';
    @Output() public select = new EventEmitter();

    public especialidades = new Array<Especialidade>();
    public crm = new Array<CRM>();
    public pacientes = new Array<PacienteModel>();
    @Input() formularioTriagem: FormGroup;
    opcaoTurno = OPCOES_TURNO_CONSULTA;
    opcaoTipoPagador = OPCAO_TIPO_PAGADOR_CONSULTA;
    consultasEmergencia: ConsultaEmergenciaModel;
    formEmergencia = this.fb.group({
        numeroConsulta: [''],
        dataHoraDaConsulta: ['', Validators.required],
        grade: [''],
        prontuario: [''],
        nome: [''],
        numeroDeSala: [''],
        turno: [''],
        tipoPagador: [''],
        especialidade: [''],
        profissional: [''],
        convenio: [''],
        clinicaCentralId: [''],
        observacao: [''],
        justificativa: [''],
        gradesDiponiveis: ['true'],
    });

    localizacao = CALENDAR_LOCALE;
    dataLimite = new Date();
    anosDisponiveis = `2010:${this.dataLimite.getFullYear()}`;
    formatoDeData = 'dd/mm/yy';
    constructor(
        private fb: FormBuilder,
        private consultaService: ConsultaService,
        private breadcrumbService: BreadcrumbService,
        private router: Router,
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

    cadastrarConsultas() {
        const cadConsulta = this.formEmergencia.value;
        const consultasEmergencia: ConsultaEmergenciaModel = {
            numeroConsulta: cadConsulta.numeroConsulta,
            dataHoraDaConsulta: cadConsulta.dataHoraDaConsulta,
            grade: cadConsulta.grade,
            prontuario: cadConsulta.prontuario,
            nome: cadConsulta.nome,
            numeroDeSala: cadConsulta.numeroDeSala,
            turno: cadConsulta.turno,
            tipoPagador: cadConsulta.tipoPagador,
            especialidade: cadConsulta.especialidade.especialidade,
            profissional: cadConsulta.profissional.nome,
            clinicaCentralId: cadConsulta.clinicaCentralId,
            observacao: cadConsulta.observacao,
            justificativa: cadConsulta.justificativa,
            condicaoDeAtendimentoId: cadConsulta.condicaoDeAtendimentoId,
            formaDeAgendamentoId: cadConsulta.formaDeAgendamentoId,
            pacienteId: cadConsulta.nome.id,
            gradesDiponiveis: cadConsulta.gradesDiponiveis,
            url: cadConsulta.url,
            id: cadConsulta.id,
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
        this.consultaService.buscarPaciente().subscribe((pacientes: Array<PacienteModel>) => {
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
