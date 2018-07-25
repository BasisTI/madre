import { BaseEntity } from '../shared';
import { Acao } from '../acao';


export class Funcionalidade implements BaseEntity {

  constructor(
    public id?: number,
    public acaos?: Acao[],
    public nm_funcionalidade?: string,
    public cd_funcionalidade?: string,
    public st_excluido?: string,
  ) {}
}
