import { CaraterDaInternacao, ICaraterDaInternacao } from './carater-da-internacao';
import { Especialidade, IEspecialidade } from './especialidade';
import { ConvenioDeSaude, IConvenioDeSaude } from './convenio-de-saude';
import { PlanoDeSaude, IPlanoDeSaude } from './plano-de-saude';
import { OrigemDaInternacao, IOrigemDaInternacao } from './origem-da-internacao';
import { Hospital, IHospital } from './hospital';
import { Procedencia, IProcedencia } from './procedencia';
import { LocalDeAtendimento, ILocalDeAtendimento } from './local-de-atendimento';
import { ModalidadeAssistencial, IModalidadeAssistencial } from './modalidade-assistencial';
import { CRM, ICRM } from './crm';

export interface IInternacao {
    id?: number;
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
}

export class Internacao implements IInternacao {
    public id?: number;
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
}
