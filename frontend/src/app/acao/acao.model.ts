import { BaseEntity } from '../shared';


export class Acao implements BaseEntity {

  constructor(
    public id?: number,
    public nm_acao?: string,
    public cd_acao?: string,
  ) {}
}
