import { BaseEntity } from '../shared';


export class PreCadastro implements BaseEntity {

  constructor(
    public id?: number,
    public nome_do_paciente?: string,
    public nome_social?: string,
    public nome_da_mae?: string,
    public data_de_nascimento?: any,
    public n_cartao_sus?: string,
    public ativo?: boolean,
  ) {}
}
