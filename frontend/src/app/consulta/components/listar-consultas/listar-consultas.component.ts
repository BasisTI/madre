import { Component, OnInit, OnDestroy, Input, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { CRM } from '@internacao/models/crm';
import { Especialidade } from '@internacao/models/especialidade';
import { BreadcrumbService, CALENDAR_LOCALE, DatatableComponent } from '@nuvem/primeng-components';
import { ConsultaEmergencia } from '../../consulta-emergencia-model';
import { ConsultaService } from '../../consulta.service';
import { ConsultaEmergenciaModel } from '../../models/consulta-emergencia-model';
import { PacienteModel } from '../../models/paciente-model';
import * as moment from 'moment';


@Component({
    selector: 'app-listar-consultas',
    templateUrl: './listar-consultas.component.html',
})
export class ListarConsultasComponent implements OnInit, OnDestroy {
    public crm = new Array<CRM>();

    public especialidades = new Array<Especialidade>();

    public pacientes = new Array<PacienteModel>();

    grade: string = '';
    numeroConsulta: string = '';
    clinicaCentralId: string = '';
    dataConsulta: string = '';
    data: Date;
    paciente: any = '';
    especialidade: any = '';
    profissional: any = '';
    prontuario: any = '';
    results = [];
    consulta: ConsultaEmergenciaModel[];

    @ViewChild('datatable')
    public datatable: DatatableComponent;

    consultas: ConsultaEmergencia[];

    listaConsultas = this.fb.group({
        prontuario: [''],
        codigo: [''],
        nome: [''],
        consulta: [''],
        codigoCentral: [''],
        consultaAnterior: [''],
        condicaoAtendimento: [''],
        especialidade: [''],
        grade: [''],
        dataInicial: [''],
        dataFinal: [''],
        historico: [''],
        consultas: [''],
        dataHora: [''],
        unidadeFuncional: [''],
        equipe: [''],
        profissional: [''],
        situacaoAtendimento: [''],
        excedente: [''],
    });

    @Input() formularioTriagem: FormGroup;
    localizacao = CALENDAR_LOCALE;
    dataLimite = new Date();
    anosDisponiveis = `2010:${this.dataLimite.getFullYear()}`;
    formatoDeData = 'dd/mm/yy';

    constructor(
        private fb: FormBuilder,
        private breadcrumbService: BreadcrumbService,
        private consultaService: ConsultaService,
    ) {}

    formatarData() {
        this.dataConsulta =
            this.data.getUTCFullYear() +
            '-' +
            ('0' + (this.data.getMonth() + 1)).slice(-2) +
            '-' +
            ('0' + this.data.getDate()).slice(-2);
    }

    ngOnInit(): void {
        this.breadcrumbService.setItems([
            {
                label: 'Consultas',
                routerLink: 'consulta',
            },
            {
                label: 'Pequisar Consultas',
                routerLink: 'listar-consultas',
            },
        ]);
        this.listar();
        this.listarEspecialidade();
        this.listarPacientes();
        this.listaProfissional();
    }

    public exportarConsultas() {
        this.consultaService.exportarConsultas().subscribe((x) => {
            const blob = new Blob([x], {
                type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
            });

            if (window.navigator && window.navigator.msSaveOrOpenBlob) {
                window.navigator.msSaveOrOpenBlob(blob);
                return;
            }
            const data = window.URL.createObjectURL(blob);
            const link = document.createElement('a');
            link.href = data;
            let dataAtual: Date = new Date();
            let dataFormatada = moment(dataAtual).format('DD-MM-YYYY_HH-mm-ss');
            let nomeArquivo: string = 'emergencias_' + dataFormatada + '.xlsx';

            link.download = nomeArquivo;
            link.dispatchEvent(
                new MouseEvent('click', { bubbles: true, cancelable: true, view: window }),
            );

            setTimeout(function () {
                window.URL.revokeObjectURL(data);
                link.remove;
            }, 100);
        });
    }

    listar() {
        if (this.data != null) {
            this.formatarData();
        }
        this.consultaService
            .getConsulta(
                this.grade,
                this.numeroConsulta,
                this.prontuario,
                this.clinicaCentralId,
                this.dataConsulta,
                this.especialidade,
                this.profissional,
                this.paciente,
            )
            .subscribe((response) => {
                this.consulta = response;
            });
    }

    public limparPesquisa() {
        this.listaConsultas.reset();
        this.grade = '';
        this.numeroConsulta = '';
        this.prontuario = '';
        this.clinicaCentralId = '';
        this.data = null;
        this.dataConsulta = '';
        this.especialidade = '';
        this.profissional = '';
        this.paciente = '';
        this.listar();
    }

    listarPacientes() {
        this.consultaService.buscarPaciente().subscribe((pacientes: Array<PacienteModel>) => {
            this.pacientes = pacientes;
        });
    }

    listaProfissional(evento?) {
        this.consultaService.buscarProfissionais().subscribe((crms: Array<CRM>) => {
            this.crm = crms;
        });
    }

    listarEspecialidade() {
        this.consultaService
            .buscarEspecialidades()
            .subscribe((especialidades: Array<Especialidade>) => {
                this.especialidades = especialidades;
            });
    }

    selecionarProfissional(event) {
        this.crm.forEach((element) => {
            this.profissional = element.nome;
        });
    }

    selecionarEspecialidade(event) {
        this.especialidade = <HTMLElement>event.target.value;
        this.especialidades.forEach((element) => {
            if (element.especialidade === this.especialidade) {
                this.especialidade = element.especialidade;
            }
        });
    }

    selecionarPaciente(event) {
        this.paciente = <HTMLElement>event.target.value;
        this.pacientes.forEach((element) => {
            if (element.nome === this.paciente) {
                this.paciente = element.id;
                this.prontuario = element.prontuario;
            }
        });
    }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }
}
