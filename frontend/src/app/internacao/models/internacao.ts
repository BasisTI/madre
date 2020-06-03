import { CRM, ICRM } from './crm';
import { CaraterDaInternacao, ICaraterDaInternacao } from './carater-da-internacao';
import { ConvenioDeSaude, IConvenioDeSaude } from './convenio-de-saude';
import { Especialidade, IEspecialidade } from './especialidade';
import { Hospital, IHospital } from './hospital';
import { ILeito, Leito } from './leito';
import { ILocalDeAtendimento, LocalDeAtendimento } from './local-de-atendimento';
import { IModalidadeAssistencial, ModalidadeAssistencial } from './modalidade-assistencial';
import { IOrigemDaInternacao, OrigemDaInternacao } from './origem-da-internacao';
import { IPlanoDeSaude, PlanoDeSaude } from './plano-de-saude';
import { IProcedencia, Procedencia } from './procedencia';

export interface IInternacao {
    id?: number;
    pacienteId?: number;
    especialidade: IEspecialidade;
    planoDeSaude: IPlanoDeSaude;
    convenioDeSaude: IConvenioDeSaude;
    caraterDaInternacao: ICaraterDaInternacao;
    origemDaInternacao: IOrigemDaInternacao;
    hospitalDeOrigem: IHospital;
    procedencia: IProcedencia;
    localDeAtendimento: ILocalDeAtendimento;
    modalidadeAssistencial: IModalidadeAssistencial;
    crm: ICRM;
    dataDaInternacao: Date;
    diferencaDeClasse: boolean;
    solicitarProntuario: boolean;
    justificativa: string;
    leito: ILeito;
}

export class Internacao implements IInternacao {
    public id?: number;
    public pacienteId?: number;
    public especialidade: Especialidade;
    public planoDeSaude: PlanoDeSaude;
    public convenioDeSaude: ConvenioDeSaude;
    public caraterDaInternacao: CaraterDaInternacao;
    public origemDaInternacao: OrigemDaInternacao;
    public hospitalDeOrigem: Hospital;
    public procedencia: Procedencia;
    public localDeAtendimento: LocalDeAtendimento;
    public modalidadeAssistencial: ModalidadeAssistencial;
    public crm: CRM;
    public dataDaInternacao: Date;
    public diferencaDeClasse: boolean;
    public solicitarProntuario: boolean;
    public justificativa: string;
    public leito: Leito;
}
