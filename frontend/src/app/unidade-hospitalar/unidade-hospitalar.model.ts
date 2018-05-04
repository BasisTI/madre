import { BaseEntity } from '../shared';


export class UnidadeHospitalar implements BaseEntity {

  constructor(
    public id?: number,
    public logoContentType?: string,
    public logo?: any,
    public sigla?: string,
    public nome?: string,
    public cnpj?: string,
    public endereco?: string,
    public ativo?: boolean,
    public logoId?: number,
  ) {}
}
