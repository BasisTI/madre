import { Component, OnInit, OnDestroy, Input, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { CRM } from '@internacao/models/crm';
import { Especialidade } from '@internacao/models/especialidade';
import { BreadcrumbService, CALENDAR_LOCALE, DatatableComponent } from '@nuvem/primeng-components';
import { ConsultaEmergencia } from '../../consulta-emergencia-model';
import { ConsultaService } from '../../consulta.service';
import { ConsultaEmergenciaModel } from '../../models/consulta-emergencia-model';
import { PacienteModel } from '../../models/paciente-model';

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

    constructor(private fb: FormBuilder, private breadcrumbService: BreadcrumbService, private consultaService: ConsultaService) {}

    formatarData(data: Date): string {
        const normalize = (x: number): string => (x < 10 ? `0${x}` : `${x}`);

        const dateObjectToFormattedString = (dateObject: Date): string => {
            const year = normalize(dateObject.getFullYear());
            const day = normalize(dateObject.getDate());
            const month = normalize(dateObject.getMonth() + 1);

            return `${year}-${month}-${day}`;
        };
        return dateObjectToFormattedString(data);
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

    listar() {
        if (this.data != null) {
            this.dataConsulta = this.formatarData(this.data);
        }
        this.consultaService
            .getConsulta(this.grade, this.numeroConsulta, this.prontuario, this.clinicaCentralId, this.dataConsulta,this.especialidade, this.profissional, this.paciente)
            .subscribe((response) => {
              this.consulta = response;  
            });
    } 
    
    public limparPesquisa() {
        this.listaConsultas.reset()
        this.grade = "";
        this.numeroConsulta = "";
        this.prontuario = "";
        this.clinicaCentralId = "";
        this.data = null;
        this.dataConsulta = "";
        this.especialidade ="";
        this.profissional ="";
        this.paciente = "";
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

    listarEspecialidade(){
        this.consultaService.buscarEspecialidades().subscribe((especialidades: Array<Especialidade>)=> {
            this.especialidades = especialidades;
        })
    }

    selecionarProfissional(event){
        this.crm.forEach(element => {
            this.profissional = element.nome
        });
    }

    selecionarEspecialidade(event){
        this.especialidade = (<HTMLElement>event.target.value);
         this.especialidades.forEach(element =>{
             if (element.especialidade === this.especialidade) {
                 this.especialidade = element.especialidade
             }
         });

     }

    selecionarPaciente(event){
        this.paciente = (<HTMLElement>event.target.value);
         this.pacientes.forEach(element =>{
             if (element.nome === this.paciente) {
                 this.paciente = element.id
                 this.prontuario = element.prontuario;
             }
         });
     }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }
}
