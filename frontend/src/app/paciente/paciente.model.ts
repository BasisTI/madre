import { BaseEntity } from '../shared';


export class Paciente implements BaseEntity {

  constructor(
    public id?: number,
    public rg?: string,
    public cpf?: string,
    public sexo?: string,
    public dataNascimento?: any,
    public cep?: string,
    public prontuario?: string,
    public nomePaciente?: string,
    public nomeSocial?: string,
    public racaCor?: string,
    public estadoCivil?: string,
    public nomePai?: string,
    public nomeMae?: string,
    public nacionalidade?: string,
    public cartaoSus?: string,
    public endereco?: string,
    public complemento?: string,
    public bairro?: string,
    public cidade?: string,
    public estado?: string,
    public telefonePrincipal?: string,
    public telefoneAlternativo?: string,
    public emailPrincipal?: string,
    public emailAlternativo?: string,
    public total?: number
  ) {}
}
