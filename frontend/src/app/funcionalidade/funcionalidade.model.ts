import { BaseEntity } from '../shared';
import { Acao } from '../acao';


export class Funcionalidade implements BaseEntity {

  constructor(
    public id?: number,
    public acaos?: Acao[],
    public nmFuncionalidade?: string,
    public cdFuncionalidade?: string,
    public stExcluido?: string,
  ) {}
}
