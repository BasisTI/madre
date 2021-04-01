import { ViewChild } from '@angular/core';
import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { CRM } from '@internacao/models/crm';
import { Especialidade } from '@internacao/models/especialidade';
import { BreadcrumbService, CALENDAR_LOCALE, DatatableComponent } from '@nuvem/primeng-components';
import { ElasticQuery } from '@shared/elastic-query';
import { ConsultaEmergencia, SearchGroup } from '../../consulta-emergencia-model';
import { ConsultaService } from '../../consulta.service';
import { PacienteModel } from '../../models/paciente-model';

@Component({
    selector: 'app-listar-consultas',
    templateUrl: './listar-consultas.component.html',
})
export class ListarConsultasComponent implements OnInit, OnDestroy {

    public crm = new Array<CRM>();

    public especialidades = new Array<Especialidade>();

    public pacientes = new Array<PacienteModel>();

    @ViewChild(DatatableComponent) datatable: DatatableComponent;

    elasticQuery: ElasticQuery = new ElasticQuery();

    searchUrl = 'madreconsulta/api/_search/consultas-emergencias';

    consulta: ConsultaEmergencia;

    valueFiltroCampo: string;

    tipoConsultaFiltro: SearchGroup;

    numeroConsulta: any = "*";
    nomePaciente: any = "*";
    grade: any = "*";
    especialidade: any = "*";
    cliniCentralId: any = "*";
    profissional: any = "*";
    paciente: any = "*";
    data: Date ;
    prontuario: any = "*";
    dataFormatada: any = "*";

    public listaConsultas = this.fb.group({
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
    constructor(private fb: FormBuilder, private breadcrumbService: BreadcrumbService, private consultaService: ConsultaService) { }

    getLabel(label) {
        return label;
    }

    valueFiltro(valuefiltro: string) {
        this.valueFiltroCampo = valuefiltro;
        this.datatable.refresh(valuefiltro);
    }

    public ngOnInit() {
        if (this.datatable) {
            this.datatable.pDatatableComponent.onRowSelect.subscribe((event) => {
                this.consulta = event.data;
            });
            this.datatable.pDatatableComponent.onRowUnselect.subscribe((event) => {
                this.consulta = undefined;
            });
        }
        this.tipoConsultaFiltro = new SearchGroup();
        this.buscaProfissional();
        this.buscaEspecialidade();
     }

    listarPacientes(event) {
        this.consultaService.buscarPaciente().subscribe((pacientes: Array<PacienteModel>) => {
            this.pacientes = pacientes;
        });
    }

    buscaProfissional() {
        this.consultaService.buscarProfissionais().subscribe((crms: Array<CRM>) => {
            this.crm = crms;
        });
    }

    buscaEspecialidade() {
        this.consultaService
            .buscarEspecialidades()
            .subscribe((especialidades: Array<Especialidade>) => {
                this.especialidades = especialidades;
            });
        }

    public limparPesquisa() {
        this.listaConsultas.reset();
        this.elasticQuery.reset();
    }

    public selectConsulta() {
        if (this.datatable && this.datatable.selectedRow) {
            if (this.datatable.selectedRow && this.datatable.selectedRow) {
                this.consulta = this.datatable.selectedRow;
            }
        }
    }

    dynamicQueryMethod() {
        let dynamicQuery = `numeroConsulta:${this.numeroConsulta} AND grade:${this.grade} AND profissional:${this.profissional} AND especialidade:${this.especialidade} AND clinicaCentralId:${this.cliniCentralId} AND pacienteId:${this.paciente} AND dataHoraDaConsulta:${this.dataFormatada}`
        return dynamicQuery;
    }

    public changeUrl() {
        let query  = this.dynamicQueryMethod();
        this.listaConsultas.reset();
        this.numeroConsulta = "*";
        this.dataFormatada = "*";
        this.grade = "*";
        this.cliniCentralId = "*";
        this.profissional = "*";
        this.especialidade = "*";
        this.paciente = "*";
        return query;
    }

    pegarGrade(event){
        this.grade = (<HTMLElement>event.target.value);
    }

     pegarNumeroConsulta(event){
         this.numeroConsulta = (<HTMLElement>event.target.value);
    }

    pegarClinica(event){
        this.cliniCentralId = (<HTMLElement>event.target.value);
    }

    pegarProfissional(event){
        this.crm.forEach(element => {
            this.profissional = element.nome
        });
    }

    pegarEspecialidade(event){
        this.especialidade =  (<HTMLElement>event.target.value);
    }
     
     pegarPaciente(event){
        this.paciente = (<HTMLElement>event.target.value);
         this.pacientes.forEach(element =>{
             if (element.nome === this.paciente) {
                 this.paciente = element.id
                 console.log(this.paciente)
             }
         });

     }

     pegaData(){
       this.dataFormatada = ((this.data.getUTCFullYear() ) + "-" + ("0" + (this.data.getMonth() + 1)).slice(-2) + "-" + ("0" + this.data.getDate()).slice(-2) + " " + this.data.getHours() + ":" + this.data.getMinutes());
       console.log(this.dataFormatada);
    }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }

}
