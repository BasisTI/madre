import { BaseEntity } from '../shared';


export class Triagem implements BaseEntity {

  constructor(
    public id?: number,
    public dataAtendimento?: any,
    public pressaoArterial?: number,
    public frequenciaRespiratoria?: number,
    public temperatura?: number,
    public peso?: number,
    public horaAtendimento?: any,
    public frequenciaCardiaca?: number,
    public glicemia?: number,
    public saturacao?: number,
    public altura?: number,
    public medicacaoContinua?: string,
    public feridaLesao?: string,
    public alergias?: string,
    public estadoPaciente?: string,
    public sintomaAlerta?: string,
    public historicoSaude?: string,
    public estadoGeral?: string,
  ) {}
}
