import { BaseEntity } from '../shared';


export class PreCadastro implements BaseEntity {

  constructor(
    public id?: number,
    public nomeDoPaciente?: string,
    public nomeSocial?: string,
    public nomeDaMae?: string,
    public dataDeNascimento?: any,
    public numCartaoSus?: string,
    public ativo?: boolean,
  ) {}
}
