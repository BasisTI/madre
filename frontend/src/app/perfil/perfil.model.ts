import { BaseEntity } from '../shared';


export class Perfil implements BaseEntity {

  constructor(
    public id?: number,
    public nmPerfil?: number,
    public dsPerfil?: string,
    public stExcluido?: boolean,
    public stAtivo?: boolean,
    public idFuncionalidade?: number,
  ) {}
}
