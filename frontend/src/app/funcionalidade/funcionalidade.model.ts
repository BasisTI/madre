import { BaseEntity } from '../shared';


export class Funcionalidade implements BaseEntity {

  constructor(
    public id?: number,
    public nm_funcionalidade?: string,
    public cd_funcionalidade?: string,
    public st_excluido?: string,
  ) {}
}
