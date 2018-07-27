import { BaseEntity } from '../shared';


export class Perfil_funcionalidade_acao implements BaseEntity {

  constructor(
    public id?: number,
    public id_perfil?: number,
    public id_funcionalidade_acao?: number,
  ) {}
}
