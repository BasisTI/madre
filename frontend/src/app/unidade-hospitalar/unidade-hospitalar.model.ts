import { BaseEntity } from '../shared';


export class UnidadeHospitalar implements BaseEntity {

  constructor(
    public id?: number,
    public logo?: any,
    public logoContentType?: string,
    public sigla?: string,
    public nome?: string,
    public cnpj?: string,
    public endereco?: string,
    public ativo?: boolean,
  ) {}
}
