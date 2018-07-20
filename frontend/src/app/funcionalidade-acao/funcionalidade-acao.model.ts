import { BaseEntity } from '../shared';


export class Funcionalidade_acao implements BaseEntity {

  constructor(
    public id?: number,
    public id_funcionalidade?: number,
    public id_acao?: number,
  ) {}
}
