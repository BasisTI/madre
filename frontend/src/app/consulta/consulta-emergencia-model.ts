import { BaseEntity } from "../shared";

export interface ConsultaEmergenciaModel {
    numeroConsulta: number;
    dataHoraDaConsulta: Date;
    grade: number;
    prontuario: string;
    nome: string;
    numeroDeSala: string;
    turno: string;
    tipoPagador: string;
    especialidade: string;
    profissional: string;
    clinicaCentralId: number;
    observacao: string;
    justificativa: string;
    condicaoDeAtendimentoId: number;
    formaDeAgendamentoId: number;
    pacienteId: number;
    gradesDiponiveis: boolean;
}

export class ConsultaEmergencia implements BaseEntity {
constructor( public id?: number,
    public url?: string,
    public numeroConsulta?: number,
    public dataHoraDaConsulta?: Date,
    public grade?: number,
    public prontuario?: string,
    public nome?: string,
    public numeroDeSala?: string,
    public turno?: string,
    public tipoPagador?: string,
    public clinicaCentralId?: number,
    public observacao?: string,
    public justificativa?: string,
    public condicaoDeAtendimentoId?: number,
    public pacienteId?: number,
    public gradesDiponiveis?: boolean,
     ) {}
 }

 export class SearchGroup {

    constructor(
        public numeroConsulta?: string,
        public grade?: string,
        public clinicaCentralId?: string
    ) {
    }
  }
 

