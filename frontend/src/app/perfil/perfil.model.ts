import { BaseEntity } from '../shared';


export class Perfil implements BaseEntity {

  constructor(
    public id?: number,
    public nomePerfil?: string,
    public dsPerfil?: string,
  ) { }
}

