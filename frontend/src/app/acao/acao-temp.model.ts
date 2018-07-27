import { BaseEntity } from '../shared';


export class AcaoTemp implements BaseEntity {

  constructor(
    public id?: number,
    public id_funcionalidade?: number,
    public nm_funcionalidade?: string,
    public cd_funcionalidade?: string,
    public nm_acao?: string,
    public cd_acao?: string,
  ) {}
}
